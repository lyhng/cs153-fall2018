package cmm;

import cmm.antlr_gen.CmmParser;
import cmm.symtab.SymbolTable;
import cmm.types.BaseType;
import cmm.types.TypeVisitor;

import java.util.List;


public class DirectCompiler extends CommonVisitor {
  private static final String PROGRAM_HEAD = ".class public CmmProgram\n" +
      ".super java/lang/Object\n";
  private static final String PROGRAM_MAIN = ".method public static main([Ljava/lang/String;)V\n" +
      "invokestatic CmmProgram/_main()I\n" +
      "return\n" +
      ".end method\n";
  private static final String PROGRAM_TAIL = "\n";

  private boolean hasMain;
  private SymbolTable symbolTable;

  DirectCompiler(SymbolTable symbolTable) {
    this.hasMain = false;
    this.symbolTable = symbolTable;
  }

  @Override
  public String visitCmm(CmmParser.CmmContext ctx) {
    String result = super.visitCmm(ctx);

    if (hasMain) {
      return PROGRAM_HEAD + result + PROGRAM_MAIN + PROGRAM_TAIL;
    }

    return PROGRAM_HEAD + result + PROGRAM_TAIL;
  }

  @Override
  public String visitFunction_identifier(CmmParser.Function_identifierContext ctx) {
    String result = super.visitFunction_identifier(ctx);
    if (result.equals("_main")) this.hasMain = true;
    return result;
  }

  @Override
  public String visitFunction_declaration(CmmParser.Function_declarationContext ctx) {
    String name = ctx.function_identifier().accept(this);

    // build function signature
    StringBuilder signature = new StringBuilder(".method private static ");
    signature.append(name).append('(');

    // parse function parameters
    List<CmmParser.Function_paramemterContext> parameters = ctx.function_paramemter();
    for (CmmParser.Function_paramemterContext parameter: parameters) {
      BaseType type = parameter.declaration_specifiers().accept(TypeVisitor.getInstance());

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
    this.symbolTable = this.symbolTable.get(name).getSymbolTable();
    String body = ctx.compound_statement().accept(this);
    this.symbolTable = this.symbolTable.getPrevious();
    String function_tail = ".end method\n";

    return function_head + body + function_tail;
  }

  @Override
  public String visitDeclaration(CmmParser.DeclarationContext ctx) {
    BaseType type = ctx.declaration_specifiers().accept(TypeVisitor.getInstance());
    List<CmmParser.Init_declaratorContext> declarators = ctx.init_declarator();
    StringBuilder builder = new StringBuilder();

    if (this.symbolTable.getLevel() == 0) {
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
