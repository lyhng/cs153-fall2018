package wci.frontend.pascal.parsers;

import java.util.EnumSet;

import wci.frontend.*;
import wci.frontend.pascal.*;
import wci.intermediate.*;
import wci.intermediate.icodeimpl.*;

import static wci.frontend.pascal.PascalTokenType.*;
import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;

/**
 *
 *
 * <h1>WhenStatementParser</h1>
 *
 * <p>Parse a WHEN statement.
 *
 * <p>For instructional purposes only. No warranties.
 */
public class WhenStatementParser extends StatementParser {
  /**
   * Constructor.
   *
   * @param parent the parent parser.
   */
  public WhenStatementParser(PascalParserTD parent) {
    super(parent);
  }

  // Synchronization set for WHEN.
  private static final EnumSet<PascalTokenType> WHEN_SET = StatementParser.STMT_START_SET.clone();

  static {
    WHEN_SET.add(ARROW);
    WHEN_SET.add(OTHERWISE);
    WHEN_SET.addAll(StatementParser.STMT_FOLLOW_SET);
  }

  /**
   * Parse an WHEN statement.
   *
   * @param token the initial token.
   * @return the root node of the generated parse tree.
   * @throws Exception if an error occurred.
   */
  public ICodeNode parse(Token token) throws Exception {
    token = nextToken(); // consume the WHEN

    // create top If Node
    ICodeNode ifNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.IF);

    // recursively parse the when statement
    parseRecurse(token, ifNode, null);

    // return the top If Node
    return ifNode;
  }

  /**
   * Recursively parses the WHEN statement
   *
   * @param token the first token of each branch
   * @param parentNode parent Node for each iteration
   * @param otherwiseNode the last node, i.e. the OTHERWISE node
   * @throws Exception if an error occurred
   */
  private void parseRecurse(Token token, ICodeNode parentNode, ICodeNode otherwiseNode)
      throws Exception {

    // Parse the expression.
    ExpressionParser expressionParser = new ExpressionParser(this);
    StatementParser statementParser = new StatementParser(this);

    // Look for an OTHERWISE.
    // terminates the recursion
    if (token.getType() == OTHERWISE) {

      token = nextToken(); // consume the OTHERWISE

      // Looks for next Arrow
      if (token.getType() == ARROW) {
        token = nextToken(); // consume the THEN
      } else {
        errorHandler.flag(token, MISSING_ARROW, this);
      }

      // Otherwise statement.
      // add statement to the otherwise node
      otherwiseNode.addChild(statementParser.parse(token));

      token = currentToken();

      // closing END word
      if (token.getType() == END) {
        token = nextToken(); // consume the END
      } else {
        errorHandler.flag(token, MISSING_END, this);
      }

      return;

    } else if (token.getType() == END) { // missing otherwise
      errorHandler.flag(token, MISSING_OTHERWISE, this);
      token = synchronize(WHEN_SET);
    } else { // Recursively parse the branches

      // add the equality expression to the parentNode
      parentNode.addChild(expressionParser.parse(token));

      // Synchronize at the ARROW.
      token = synchronize(WHEN_SET);
      if (token.getType() == ARROW) {
        token = nextToken(); // consume the ARROW
      } else {
        errorHandler.flag(token, MISSING_ARROW, this);
      }

      // Parse the executing statement.
      // The IF node adopts the statement subtree as its second child.

      parentNode.addChild(statementParser.parse(token));
      token = currentToken();

      // checks for semicolon between branches
      token = synchronize(WHEN_SET);
      if (token.getType() != SEMICOLON) {
        errorHandler.flag(token, MISSING_SEMICOLON, this);
      }

      token = nextToken();

      // Create an IF node only if the next token is not OTHERWISE
      // and add it to the parentNode
      ICodeNode ifNode = null;
      if (token.getType() != OTHERWISE) {
        ifNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.IF);
        parentNode.addChild(ifNode);
      }

      // Recursively parse the next branch
      parseRecurse(token, ifNode, parentNode);
    }
  }
}
