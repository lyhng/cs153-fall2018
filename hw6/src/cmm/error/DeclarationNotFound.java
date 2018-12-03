package cmm.error;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class DeclarationNotFound extends StringError {
  public DeclarationNotFound(ParserRuleContext context, String name) {
    super(context, "'%s' is undeclared", name);
  }

  public DeclarationNotFound(TerminalNode node, String name) {
    super(node, "'%s' is undeclared", name);
  }
}
