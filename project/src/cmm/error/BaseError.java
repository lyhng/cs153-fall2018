package cmm.error;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public abstract class BaseError {
  private int line;
  private int col;
  private int length;

  public BaseError(ParserRuleContext context) {
    Token start = context.start;
    Token stop = context.stop;

    if (start.getLine() == stop.getLine())  {
      this.line = start.getLine();
      this.col = start.getCharPositionInLine();
      this.length = stop.getStopIndex() - start.getStartIndex();
    } else {
      // TODO: figure out way to show error message for multi-line errors
    }
  }

  public BaseError(int line, int col, int length) {
    this.line = line;
    this.col = col;
    this.length = length;
  }

  public BaseError(TerminalNode node) {
    Token token = node.getSymbol();
    this.line = token.getLine();
    this.col = token.getCharPositionInLine();
    this.length = token.getStopIndex() - token.getStartIndex();
  }

  abstract String getErrorMessage();

  public String toReadableMessage(String[] lines) {
    if (this.line > lines.length) {
      // unable to get source line
      // TODO: record this as warning
      return "";
    }

    String prefix = String.format("%s:%s: error: ", this.line, this.col);
    String line = "    " + lines[this.line-1];
    String space = new String(new char[col]).replace("\0", " ");
    String tilde = new String(new char[length]).replace("\0", "~");
    String marker = String.format("    %s^%s", space, tilde);

    return prefix + this.getErrorMessage() + "\n" + line + "\n" + marker + "\n";
  }
}
