package cmm;

import cmm.antlr_gen.CmmBaseVisitor;
import cmm.antlr_gen.CmmParser;

public class CommonVisitor extends CmmBaseVisitor<String> {
  public String visitFunction_identifier(CmmParser.Function_identifierContext ctx) {
    String result = ctx.Identifier().getText();

    if (result.equals("main")) {
      return "_" + result;
    } else if (result.matches("^_*main$")) {
      return "_" + result;
    }

    return result;
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
}
