package cmm;

import cmm.antlr_gen.CmmBaseVisitor;
import cmm.antlr_gen.CmmParser;
import cmm.types.BaseType;
import cmm.types.TypeVisitor;
import wci.intermediate.SymTabEntry;
import wci.intermediate.SymTabFactory;
import wci.intermediate.SymTabStack;
import wci.intermediate.symtabimpl.DefinitionImpl;
import wci.intermediate.symtabimpl.Predefined;

import java.util.List;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_SYMTAB;


public class DirectCompiler extends CmmBaseVisitor<String> {
  private static final String PROGRAM_HEAD = ".class public CmmProgram\n" +
      ".super java/lang/Object\n";
  private static final String PROGRAM_TAIL = "\n";

  private SymTabStack symbolTableStack;

  DirectCompiler() {
    this.symbolTableStack = SymTabFactory.createSymTabStack();
    Predefined.initialize(symbolTableStack);
  }

  @Override
  public String visitCmm(CmmParser.CmmContext ctx) {
    String result = super.visitCmm(ctx);
    return PROGRAM_HEAD + result + PROGRAM_TAIL;
  }

  @Override
  public String visitFunction_declaration(CmmParser.Function_declarationContext ctx) {
    String name = ctx.declarator().Identifier().toString();

    SymTabEntry function = symbolTableStack.enterLocal(name);
    function.setDefinition(DefinitionImpl.FUNCTION);
    function.setAttribute(ROUTINE_SYMTAB, symbolTableStack.push());
    symbolTableStack.setProgramId(function);

    // build function signature
    StringBuilder signature = new StringBuilder(".method private static ");
    signature.append(name).append('(');

    // parse function parameters
    List<CmmParser.Function_paramemterContext> parameters = ctx.function_paramemter();
    for (CmmParser.Function_paramemterContext parameter: parameters) {
      BaseType type = parameter.declaration_specifiers().accept(TypeVisitor.getInstance());
      String identifier = parameter.declarator().accept(this);

      // TODO: push identifier to local symbol table
      signature.append(type.toJasminType());
    }
    signature.append(')');

    // function return type
    BaseType typename = ctx.declaration_specifiers().accept(TypeVisitor.getInstance());
    signature.append(typename.toJasminType());
    signature.append('\n');

    // build function body
    String function_head = signature.toString();
    String body = ctx.compound_statement().accept(this);
    String function_tail = ".end method\n";

    symbolTableStack.pop();

    return function_head + body + function_tail;
  }

  @Override
  public String visitBuiltin_types(CmmParser.Builtin_typesContext ctx) {
    if (ctx.Identifier() == null)
      return ctx.getText();
    return ctx.Identifier().toString();
  }

  @Override
  public String visitDeclarator(CmmParser.DeclaratorContext ctx) {
    return ctx.Identifier().toString();
  }

  @Override
  public String visitDeclaration(CmmParser.DeclarationContext ctx) {
    BaseType type = ctx.declaration_specifiers().accept(TypeVisitor.getInstance());
    List<CmmParser.Init_declaratorContext> declarators = ctx.init_declarator();
    StringBuilder builder = new StringBuilder();

    if (this.symbolTableStack.getCurrentNestingLevel() == 0) {
      // global variable

      for (CmmParser.Init_declaratorContext declarator: declarators) {
        String name = declarator.declarator().accept(this);
        builder.append(".field private static ").append(name).append(' ').append(type.toJasminType()).append('\n');
      }

      return builder.toString();
    } else {
      // local variable
    }

    return "";
  }

  @Override
  protected String defaultResult() {
    return "";
  }

  @Override
  protected String aggregateResult(String aggregate, String nextResult) {
    return aggregate + nextResult;
  }
}
