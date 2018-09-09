package wci.frontend.java.tokens;

import wci.frontend.Source;
import wci.frontend.java.JavaToken;

import static wci.frontend.Source.EOF;
import static wci.frontend.java.JavaErrorCode.UNEXPECTED_EOF;
import static wci.frontend.java.JavaTokenType.ERROR;
import static wci.frontend.java.JavaTokenType.STRING;

/**
 *
 *
 * <h1>JavaStringToken</h1>
 *
 * <p>Java string tokens.
 *
 * <p>Copyright (c) 2009 by Ronald Mak
 *
 * <p>For instructional purposes only. No warranties.
 */
public class JavaStringToken extends JavaToken {
  /**
   * Constructor.
   *
   * @param source the source from where to fetch the token's characters.
   * @throws Exception if an error occurred.
   */
  public JavaStringToken(Source source) throws Exception {
    super(source);
  }

  /**
   * Extract a Java string token from the source.
   *
   * @throws Exception if an error occurred.
   */
  protected void extract() throws Exception {
    StringBuilder textBuffer = new StringBuilder();
    StringBuilder valueBuffer = new StringBuilder();

    char currentChar = nextChar(); // consume initial quote
    textBuffer.append('\'');

    // Get string characters.
    do {
      // Replace any whitespace character with a blank.
      if (Character.isWhitespace(currentChar)) {
        currentChar = ' ';
      }

      if ((currentChar != '\'') && (currentChar != EOF)) {
        textBuffer.append(currentChar);
        valueBuffer.append(currentChar);
        currentChar = nextChar(); // consume character
      }

      // Quote?  Each pair of adjacent quotes represents a single-quote.
      if (currentChar == '\'') {
        while ((currentChar == '\'') && (peekChar() == '\'')) {
          textBuffer.append("''");
          valueBuffer.append(currentChar); // append single-quote
          currentChar = nextChar(); // consume pair of quotes
          currentChar = nextChar();
        }
      }
    } while ((currentChar != '\'') && (currentChar != EOF));

    if (currentChar == '\'') {
      nextChar(); // consume final quote
      textBuffer.append('\'');

      type = STRING;
      value = valueBuffer.toString();
    } else {
      type = ERROR;
      value = UNEXPECTED_EOF;
    }

    text = textBuffer.toString();
  }
}
