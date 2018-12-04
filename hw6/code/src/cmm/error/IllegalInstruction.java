package cmm.error;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class IllegalInstruction extends Exception {

  public class IllegalInstructionError extends StringError {
    IllegalInstructionError(ParserRuleContext context, String format, Object... values) {
      super(context, format, values);
    }

    IllegalInstructionError(TerminalNode node, String format, Object... values) {
      super(node, format, values);
    }
  }

  private IllegalInstruction() {}

  public IllegalInstruction(String message) {
    super(message);
  }

  public IllegalInstructionError toError(ParserRuleContext ctx) {
    return new IllegalInstructionError(ctx, this.getMessage());
  }

  public IllegalInstructionError toError(TerminalNode node) {
    return new IllegalInstructionError(node, this.getMessage());
  }
}
