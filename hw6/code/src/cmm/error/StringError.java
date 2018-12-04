package cmm.error;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class StringError extends BaseError {
  private String message;

  public StringError(ParserRuleContext context, String format, Object... values) {
    super(context);
    this.message = String.format(format, (Object[]) values);
  }

  public StringError(TerminalNode node, String format, Object... values) {
    super(node);
    this.message = String.format(format, (Object[]) values);
  }

  @Override
  String getErrorMessage() {
    return this.message;
  }
}
