package cmm;

import cmm.antlr_gen.CmmParser;
import cmm.symtab.Symbol;
import cmm.symtab.SymbolTable;
import cmm.types.BaseType;
import cmm.types.FunctionType;
import cmm.types.TypeFactory;

import java.util.Map;


public class DirectCompiler extends CommonVisitor {
  private static final String PROGRAM_HEAD = ".class public CmmProgram\n" +
      ".super java/lang/Object\n";
  private static final String PROGRAM_MAIN = ".method public static main([Ljava/lang/String;)V\n" +
      "invokestatic CmmProgram/_main()V\n" +
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
    String name = super.visit(ctx.function_identifier());
    Symbol function_symbol = this.symbolTable.get(name);

    if (!(function_symbol.getType() instanceof FunctionType)) {
      // TODO: throw an exception. trying to create non function type
      return "";
    }

    FunctionType function = (FunctionType)function_symbol.getType();

    // build function body
    String function_head = ".method private static " + function.buildSignature() + "\n";
    this.symbolTable = function.getSymbolTable();
    int size = this.symbolTable.size() + 1;
    String limits = ".limit locals " + size + "\n.limit stack " + size + "\n";
    String body = ctx.compound_statement().accept(this);
    this.symbolTable = this.symbolTable.getPrevious();
    String function_tail = "return\n.end method\n";

    return function_head + limits + body + function_tail;
  }

  // region Expression
  // ---------------------------------------------------------------------------------------

  @Override
  public String visitDecimalNumber(CmmParser.DecimalNumberContext ctx) {
    String number = ctx.DecimalNumber().toString();
    int n = Integer.valueOf(number);

    if (n <= 5 && n >= 0) {
      return "iconst_" + number + "\n";
    } else if (n == -1) {
      return "iconst_m1\n";
    }

    return "bipush   " + number + "\n";
  }

  @Override
  public String visitFloatingNumber(CmmParser.FloatingNumberContext ctx) {
    String number = ctx.FloatingNumber().toString();

    return "ldc " + number + "\n";
  }

  // TODO: string

  @Override
  public String visitPrimary_expression(CmmParser.Primary_expressionContext ctx) {
    if (ctx.Identifier() != null) {
      String name = ctx.Identifier().toString();
      Symbol symbol = symbolTable.get(name);
      return symbol.load();
    }
    return super.visitPrimary_expression(ctx);
  }

  @Override
  public String visitFunctionCall(CmmParser.FunctionCallContext ctx) {
    // TODO: function call
    return super.visitFunctionCall(ctx);
  }

  // TODO: postfix & unary operations

  @Override
  public String visitMultiplicative_expression(CmmParser.Multiplicative_expressionContext ctx) {
    if (ctx.multiplicative_operator() != null) {
      String operator = ctx.multiplicative_operator().getText();

      CmmParser.Multiplicative_expressionContext left = ctx.multiplicative_expression();
      CmmParser.Unary_expressionContext right = ctx.unary_expression();

      String opl = visit(left);
      String opr = visit(right);

      BaseType resultType;
      try {
        resultType = left.type.canCastTo(right.type) ? left.type : right.type;
      } catch (Exception e) {
        e.printStackTrace();
        return opl + opr;
      }

      switch (operator) {
        case "*":
          return opl + opr + resultType.mul();
        case "/":
          return opl + opr + resultType.div();
        case "%":
          return opl + opr + resultType.rem();
      }
      // TODO: should throw an exception here
      return opl + opr;
    }
    String result = super.visitMultiplicative_expression(ctx);
    return result;
  }

  @Override
  public String visitAdditive_expression(CmmParser.Additive_expressionContext ctx) {
    if (ctx.additive_operator() != null) {
      String operator = ctx.additive_operator().getText();

      CmmParser.Additive_expressionContext left = ctx.additive_expression();
      CmmParser.Multiplicative_expressionContext right = ctx.multiplicative_expression();

      String opl = visit(left);
      String opr = visit(right);

      BaseType resultType;
      try {
        resultType = left.type.canCastTo(right.type) ? left.type : right.type;
      } catch (Exception e) {
        e.printStackTrace();
        return opl + opr;
      }

      switch (operator) {
        case "+":
          return opl + opr + resultType.add();
        case "-":
          return opl + opr + resultType.sub();
      }
      // TODO: should throw an exception here
      return opl + opr;
    }
    String result = super.visitAdditive_expression(ctx);
    return result;
  }

  @Override
  public String visitAssignment_expression(CmmParser.Assignment_expressionContext ctx) {
    if (ctx.assignment_operator() != null) {
      String name = ctx.Identifier().toString();
      Symbol symbol = symbolTable.get(name);

      return visit(ctx.expression()) + symbol.store();
    }

    return super.visitAssignment_expression(ctx);
  }

  // endregion

  @Override
  public String visitStatement(CmmParser.StatementContext ctx) {
    return String.format("; %s\n", ctx.getText()) + super.visitStatement(ctx);
  }

  @Override
  public String visitDeclaration(CmmParser.DeclarationContext ctx) {
    return String.format("; %s\n", ctx.getText()) + super.visitDeclaration(ctx);
  }

  @Override
  public String visitInit_declarator(CmmParser.Init_declaratorContext ctx) {
    if (ctx.initializer() != null) {
      String name = super.visitDeclarator(ctx.declarator());
      Symbol symbol = symbolTable.get(name);

      return visit(ctx.initializer()) + symbol.store();
    }
    return super.visitInit_declarator(ctx);
  }

  @Override
  public String visitBuiltin_types(CmmParser.Builtin_typesContext ctx) {
    return "";
  }

  @Override
  public String visitDeclarator(CmmParser.DeclaratorContext ctx) {
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
