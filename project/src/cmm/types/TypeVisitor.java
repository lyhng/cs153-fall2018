package cmm.types;

import cmm.antlr_gen.CmmBaseVisitor;
import cmm.antlr_gen.CmmParser;

public class TypeVisitor extends CmmBaseVisitor<BaseType> {
  private static TypeVisitor instance;

  private TypeVisitor() {}

  public static TypeVisitor getInstance() {
    if (TypeVisitor.instance == null) {
      TypeVisitor.instance = new TypeVisitor();
    }

    return TypeVisitor.instance;
  }

  @Override
  public BaseType visitBuiltin_types(CmmParser.Builtin_typesContext ctx) {
    if (ctx.Identifier() != null) {
      return TypeFactory.fromString(ctx.Identifier().toString());
    }
    return TypeFactory.fromString(ctx.getText());
  }
}
