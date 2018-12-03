package cmm.error;

import org.antlr.v4.runtime.ParserRuleContext;

public class StringError extends BaseError {
  private String message;

  public StringError(ParserRuleContext context, String message) {
    super(context);
    this.message = message;
  }

  public StringError(ParserRuleContext context, String format, Object... values) {
    super(context);
    this.message = String.format(format, (Object[]) values);
  }

  @Override
  String getErrorMessage() {
    return this.message;
  }
}
