package cmm;

import cmm.antlr_gen.CmmParser;
import cmm.symtab.Symbol;
import cmm.symtab.SymbolTable;
import cmm.types.BaseType;
import cmm.types.TypeVisitor;

import java.util.List;
import java.util.Map;


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

  private String buildFields() {
    StringBuilder builder = new StringBuilder();
    for (Map.Entry<String, Symbol> entry : this.symbolTable.entrySet()) {
      Symbol symbol = entry.getValue();
      if (symbol.getKind() != Symbol.SymbolKind.DECLARATION) continue;

      String name = entry.getKey();
      builder.append(".field private static ");
      builder.append(name).append(' ');
      builder.append(symbol.getType().toJasminType()).append('\n');
    }
    return builder.toString();
  }

  @Override
  public String visitCmm(CmmParser.CmmContext ctx) {
    StringBuilder builder = new StringBuilder().append(PROGRAM_HEAD);
    builder.append(this.buildFields());
    builder.append(super.visitCmm(ctx));
    if (hasMain) {
      builder.append(PROGRAM_MAIN);
    }
    builder.append(PROGRAM_TAIL);
    return builder.toString();
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
    Symbol function = this.symbolTable.get(name);

    // build function body
    String function_head = ".method private static " + function.buildSignature() + "\n";
    this.symbolTable = function.getSymbolTable();
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
      // global variable is built at visitCmm
      return "";
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
