package cmm;

import cmm.antlr_gen.CmmParser;
import cmm.symtab.Symbol;
import cmm.symtab.SymbolTable;
import cmm.types.BaseType;
import cmm.types.FunctionType;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DirectCompiler extends CommonVisitor {
  private static final String PROGRAM_HEAD =
      ".class public CmmProgram\n" + ".super java/lang/Object\n";
  private static final String PROGRAM_MAIN =
      ".method public static main([Ljava/lang/String;)V\n"
          + "invokestatic CmmProgram/_main()V\n"
          + "return\n"
          + ".end method\n";
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
      if (symbol.getType() instanceof FunctionType) continue;

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
    Symbol function_symbol = this.symbolTable.lookup(name);

    if (!(function_symbol.getType() instanceof FunctionType)) {
      // TODO: throw an exception. trying to create non function type
      return "";
    }

    FunctionType function = (FunctionType) function_symbol.getType();

    // build function body
    String function_head = ".method private static " + function.buildSignature() + "\n";
    this.symbolTable = function.getSymbolTable();
    int size = this.symbolTable.size() + 1;
    String limits = ".limit locals " + size + "\n.limit stack " + size + "\n";
    String body = ctx.compound_statement().accept(this);
    this.symbolTable = this.symbolTable.getPrevious();
    String function_tail = ".end method\n";

    if (!body.endsWith("return\n")) {
      function_tail = "return\n" + function_tail;
    }

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
      Symbol symbol = symbolTable.lookup(name);
      return symbol.load();
    }
    return super.visitPrimary_expression(ctx);
  }

  @Override
  public String visitFunctionCall(CmmParser.FunctionCallContext ctx) {
    String name = visit(ctx.function_identifier());
    Symbol symbol = symbolTable.lookup(name);
    BaseType type = symbol.getType();

    if (!(type instanceof FunctionType)) {
      // TODO: error
      return super.visitFunctionCall(ctx);
    }

    FunctionType function = (FunctionType) type;

    // parameter
    // TODO: parameter type validation

    StringBuilder arguments_ops = new StringBuilder();
    List<CmmParser.ExpressionContext> arguments = ctx.expression();
    for (CmmParser.ExpressionContext argument : arguments) {
      arguments_ops.append(argument.accept(this));
    }

    SymbolTable table = symbolTable.lookupTable(name);

    arguments_ops.append(String.format("invokestatic %s/%s\n", table.getName().replace('.', '/'), function.buildSignature()));

    return arguments_ops.toString();
  }

  // TODO: postfix & unary operations

  private String operator_expression(ParserRuleContext left_ctx, ParserRuleContext right_ctx,
                                     BaseType left_type, BaseType right_type,
                                     Function<BaseType, String> instruction) {
//    System.out.printf("left: %s, right: %s\n", left_ctx.getText(), right_ctx.getText());
//    System.out.printf("left type: %s, right type: %s\n\n", left_type, right_type);
    BaseType type;
    try {
      type = left_type.tryCastTo(right_type);
    } catch (Exception e) {
      return "";
    }

    return visit(left_ctx) + visit(right_ctx) + instruction.apply(type);
  }

  @Override
  public String visitMultiplicative_expression(CmmParser.Multiplicative_expressionContext ctx) {
    if (ctx.multiplicative_operator() != null) {
      String operator = ctx.multiplicative_operator().getText();

      return operator_expression(ctx.multiplicative_expression(), ctx.unary_expression(),
          ctx.multiplicative_expression().type, ctx.unary_expression().type,
          (type) -> {
            switch (operator) {
              case "*":
                return type.mul();
              case "/":
                return type.div();
              case "%":
                return type.rem();
            }
            return "";
          });
    }
    return super.visitMultiplicative_expression(ctx);
  }

  @Override
  public String visitAdditive_expression(CmmParser.Additive_expressionContext ctx) {
    if (ctx.additive_operator() != null) {
      String operator = ctx.additive_operator().getText();

      return operator_expression(ctx.additive_expression(), ctx.multiplicative_expression(),
          ctx.additive_expression().type, ctx.multiplicative_expression().type,
          (type) -> {
            switch (operator) {
              case "+":
                return type.add();
              case "-":
                return type.sub();
            }
            return "";
          });
    }
    return super.visitAdditive_expression(ctx);
  }

  @Override
  public String visitShift_expression(CmmParser.Shift_expressionContext ctx) {
    if (ctx.shift_operator() != null) {
      String operator = ctx.shift_operator().getText();

      return operator_expression(ctx.shift_expression(), ctx.additive_expression(),
          ctx.shift_expression().type, ctx.additive_expression().type,
          (type) -> {
            switch (operator) {
              case "<<":
                return type.shl();
              case ">>":
                return type.shr();
            }
            return "";
          });
    }
    return super.visitShift_expression(ctx);
  }

  @Override
  public String visitRelational_expression(CmmParser.Relational_expressionContext ctx) {
    if (ctx.relational_operator() != null) {
      String operator = ctx.relational_operator().getText();

      return operator_expression(ctx.relational_expression(), ctx.shift_expression(),
          ctx.relational_expression().type, ctx.shift_expression().type,
          (type) -> {
            switch (operator) {
              case ">":
                return type.gt();
              case ">=":
                return type.ge();
              case "<":
                return type.lt();
              case "<=":
                return type.le();
            }
            return "";
          });
    }
    return super.visitRelational_expression(ctx);
  }

  @Override
  public String visitEquality_expression(CmmParser.Equality_expressionContext ctx) {
    if (ctx.equality_expression() != null) {
      String operator = ctx.equality_operator().getText();

      return operator_expression(ctx.equality_expression(), ctx.relational_expression(),
          ctx.equality_expression().type, ctx.relational_expression().type,
          (type) -> {
            switch (operator) {
              case "==":
                return type.eq();
              case "!=":
                return type.ne();
            }
            return "";
          });
    }
    return super.visitEquality_expression(ctx);
  }

  @Override
  public String visitAnd_expression(CmmParser.And_expressionContext ctx) {
    if (ctx.and_expression() != null) {
      return operator_expression(ctx.and_expression(), ctx.equality_expression(),
          ctx.and_expression().type, ctx.equality_expression().type,
          BaseType::and);
    }
    return super.visitAnd_expression(ctx);
  }

  @Override
  public String visitXor_expression(CmmParser.Xor_expressionContext ctx) {
    if (ctx.xor_expression() != null) {
      return operator_expression(ctx.xor_expression(), ctx.and_expression(),
          ctx.xor_expression().type, ctx.and_expression().type,
          BaseType::xor);
    }
    return super.visitXor_expression(ctx);
  }

  @Override
  public String visitOr_expression(CmmParser.Or_expressionContext ctx) {
    if (ctx.or_expression() != null) {
      return operator_expression(ctx.or_expression(), ctx.xor_expression(),
          ctx.or_expression().type, ctx.xor_expression().type,
          BaseType::or);
    }
    return super.visitOr_expression(ctx);
  }

  @Override
  public String visitAssignment_expression(CmmParser.Assignment_expressionContext ctx) {
    if (ctx.assignment_operator() != null) {
      String name = ctx.Identifier().toString();
      Symbol symbol = symbolTable.lookup(name);

      return visit(ctx.expression()) + symbol.store();
    }

    return super.visitAssignment_expression(ctx);
  }

  // endregion

  // region Control Statement

  @Override
  public String visitSelection_statement(CmmParser.Selection_statementContext ctx) {
    String result = visit(ctx.expression());
    String end = LabelAssigner.getInstance().getLabel();

    result += "iconst_1\n";
    result += "if_icmpne " + end + "\n";    // if condition is false, go to end or else
    result += visit(ctx.statement(0));

    if (ctx.statement(1) != null) {
      String realend = LabelAssigner.getInstance().getLabel();
      result += "goto " + realend + "\n";
      result += end + ":\n";
      result += visit(ctx.statement(1));
      result += realend + ":\n";
    } else {
      result += end + ":\n";
    }

    return result;
  }

  // endregion

  @Override
  public String visitJump_statement(CmmParser.Jump_statementContext ctx) {
    String kind = ctx.getChild(0).getText();

    switch (kind) {
      case "return":
        {
          String result = "";

          if (ctx.expression() != null) {
            result += visit(ctx.expression());
          }

          // TODO: return based on function return type
          result += ctx.expression().type.return_();

          return result;
        }
    }
    return super.visitJump_statement(ctx);
  }

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
      Symbol symbol = symbolTable.lookup(name);

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
