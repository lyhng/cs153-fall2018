package cmm.error;

import cmm.types.BaseType;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TypeError extends Exception {

  public class TypeCompilerError extends StringError {
    public TypeCompilerError(ParserRuleContext context, String format, Object... values) {
      super(context, format, values);
    }

    public TypeCompilerError(TerminalNode node, String format, Object... values) {
      super(node, format, values);
    }
  }

  BaseType expect;
  BaseType actual;

  public TypeError(BaseType expect, BaseType actual) {
    super();
    this.expect = expect;
    this.actual = actual;
  }

  @Override
  public String getMessage() {
    return String.format("Type Error, expected type '%s' got '%s'.", this.expect, this.actual);
  }

  public TypeCompilerError toError(ParserRuleContext ctx) {
    return new TypeCompilerError(ctx, this.getMessage());
  }
}
