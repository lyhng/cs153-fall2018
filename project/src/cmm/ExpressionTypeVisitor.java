package cmm;

import cmm.antlr_gen.CmmParser;
import cmm.error.BaseError;
import cmm.error.DeclarationNotFound;
import cmm.error.StringError;
import cmm.symtab.Symbol;
import cmm.symtab.SymbolTable;
import cmm.types.BaseType;
import cmm.types.FunctionType;
import cmm.types.TypeFactory;

import java.util.ArrayList;
import java.util.List;

public class ExpressionTypeVisitor extends CommonVisitor {
  private final ArrayList<BaseError> errors;
  private SymbolTable symbolTable;

  ExpressionTypeVisitor(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.errors = new ArrayList<>();
  }

  public ArrayList<BaseError> getErrors() {
    return errors;
  }

  @Override
  public String visitPrimary_expression(CmmParser.Primary_expressionContext ctx) {
    if (ctx.constant() != null) {
      if (ctx.constant() instanceof CmmParser.DecimalNumberContext) {
        ctx.type = TypeFactory.INT_TYPE;
      } else if (ctx.constant() instanceof CmmParser.FloatingNumberContext) {
        ctx.type = TypeFactory.FLOAT_TYPE;
      } else if (ctx.constant() instanceof CmmParser.StringContext) {
        // TODO: string type
      } else if (ctx.constant() instanceof CmmParser.CharacterContext) {
        ctx.type = TypeFactory.CHAR_TYPE;
      }

      return visit(ctx.constant());
    } else if (ctx.expression() != null) {
      String result = visit(ctx.expression());
      ctx.type = ctx.expression().type;
      return result;
    }

    // Identifier
    String name = ctx.Identifier().toString();
    Symbol symbol = symbolTable.lookup(name);

    if (symbol == null) {
      this.errors.add(new DeclarationNotFound(ctx.Identifier(), name));
      return "";
    }

    ctx.type = symbol.getType();

    return visit(ctx.Identifier());
  }

  @Override
  public String visitPrimaryExpression(CmmParser.PrimaryExpressionContext ctx) {
    String result = super.visitPrimaryExpression(ctx);
    ctx.type = ctx.primary_expression().type;
    return result;
  }

  // TODO: expression type for array expression

  @Override
  public String visitFunctionCall(CmmParser.FunctionCallContext ctx) {
    String name = super.visit(ctx.function_identifier());
    Symbol symbol = symbolTable.lookup(name);

    if (symbol == null) {
      this.errors.add(new DeclarationNotFound(ctx.function_identifier(), name));
      return "";
    }

    BaseType type = symbol.getType();

    if (!(type instanceof FunctionType)) {
      this.errors.add(new StringError(ctx.function_identifier(), "'%s' is not a function", name));
      return this.defaultResult();
    }

    List<CmmParser.ExpressionContext> arguments = ctx.expression();
    for (CmmParser.ExpressionContext argument : arguments) {
      visit(argument);
    }

    ctx.type = ((FunctionType) type).getReturnType();
    return this.defaultResult();
  }

  @Override
  public String visitPostfix_expression(CmmParser.Postfix_expressionContext ctx) {
    String result = super.visitPostfix_expression(ctx);
    if (ctx.function_array_expression() != null) ctx.type = ctx.function_array_expression().type;
    return result;
  }

  @Override
  public String visitUnary_expression(CmmParser.Unary_expressionContext ctx) {
    String result = super.visitUnary_expression(ctx);
    if (ctx.postfix_expression() != null) ctx.type = ctx.postfix_expression().type;
    return result;
  }

  @Override
  public String visitMultiplicative_expression(CmmParser.Multiplicative_expressionContext ctx) {
    String result = super.visitMultiplicative_expression(ctx);
    if (ctx.unary_expression() != null) ctx.type = ctx.unary_expression().type;
    return result;
  }

  @Override
  public String visitAdditive_expression(CmmParser.Additive_expressionContext ctx) {
    String result = super.visitAdditive_expression(ctx);
    if (ctx.multiplicative_expression() != null) ctx.type = ctx.multiplicative_expression().type;
    return result;
  }

  @Override
  public String visitShift_expression(CmmParser.Shift_expressionContext ctx) {
    String result = super.visitShift_expression(ctx);
    if (ctx.additive_expression() != null) ctx.type = ctx.additive_expression().type;
    return result;
  }

  @Override
  public String visitRelational_expression(CmmParser.Relational_expressionContext ctx) {
    String result = super.visitRelational_expression(ctx);
    if (ctx.shift_expression() != null) ctx.type = ctx.shift_expression().type;
    return result;
  }

  @Override
  public String visitEquality_expression(CmmParser.Equality_expressionContext ctx) {
    String result = super.visitEquality_expression(ctx);
    if (ctx.relational_expression() != null) ctx.type = ctx.relational_expression().type;
    return result;
  }

  @Override
  public String visitAnd_expression(CmmParser.And_expressionContext ctx) {
    String result = super.visitAnd_expression(ctx);
    if (ctx.equality_expression() != null) ctx.type = ctx.equality_expression().type;
    return result;
  }

  @Override
  public String visitXor_expression(CmmParser.Xor_expressionContext ctx) {
    String result = super.visitXor_expression(ctx);
    if (ctx.and_expression() != null) ctx.type = ctx.and_expression().type;
    return result;
  }

  @Override
  public String visitOr_expression(CmmParser.Or_expressionContext ctx) {
    String result = super.visitOr_expression(ctx);
    if (ctx.xor_expression() != null) ctx.type = ctx.xor_expression().type;
    return result;
  }

  @Override
  public String visitLogical_and_expression(CmmParser.Logical_and_expressionContext ctx) {
    String result = super.visitLogical_and_expression(ctx);
    if (ctx.or_expression() != null) ctx.type = ctx.or_expression().type;
    return result;
  }

  @Override
  public String visitLogical_or_expression(CmmParser.Logical_or_expressionContext ctx) {
    String result = super.visitLogical_or_expression(ctx);
    if (ctx.logical_and_expression() != null) ctx.type = ctx.logical_and_expression().type;
    return result;
  }

  @Override
  public String visitTernary_expression(CmmParser.Ternary_expressionContext ctx) {
    String result = super.visitTernary_expression(ctx);
    if (ctx.logical_or_expression() != null) ctx.type = ctx.logical_or_expression().type;
    return result;
  }

  @Override
  public String visitAssignment_expression(CmmParser.Assignment_expressionContext ctx) {
    String result = super.visitAssignment_expression(ctx);
    if (ctx.ternary_expression() != null) ctx.type = ctx.ternary_expression().type;
    return result;
  }

  @Override
  public String visitComma_expression(CmmParser.Comma_expressionContext ctx) {
    String result = super.visitComma_expression(ctx);
    if (ctx.assignment_expression() != null) ctx.type = ctx.assignment_expression().type;
    return result;
  }

  @Override
  public String visitExpression(CmmParser.ExpressionContext ctx) {
    String result = super.visitExpression(ctx);
    if (ctx.comma_expression() != null) ctx.type = ctx.comma_expression().type;
    return result;
  }

  @Override
  public String visitFunction_declaration(CmmParser.Function_declarationContext ctx) {
    String name = super.visit(ctx.function_identifier());
    Symbol function_symbol = this.symbolTable.lookup(name);

    if (function_symbol == null) {
      this.errors.add(new DeclarationNotFound(ctx.function_identifier(), name));
      return "";
    }

    if (!(function_symbol.getType() instanceof FunctionType)) {
      if (!(function_symbol.getType() instanceof FunctionType)) {
        this.errors.add(new StringError(ctx.function_identifier(), "'%s' is not declared as a function.", name));
        return "";
      }
      return "";
    }

    FunctionType function = (FunctionType) function_symbol.getType();
    this.symbolTable = function.getSymbolTable();
    String result = super.visitFunction_declaration(ctx);
    this.symbolTable = this.symbolTable.getPrevious();

    return result;
  }
}
