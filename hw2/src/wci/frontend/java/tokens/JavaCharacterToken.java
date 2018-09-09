package wci.frontend.java.tokens;

import wci.frontend.Source;
import wci.frontend.java.JavaToken;

import static wci.frontend.Source.EOF;
import static wci.frontend.java.JavaErrorCode.INVALID_CHARACTER;
import static wci.frontend.java.JavaTokenType.ERROR;
import static wci.frontend.java.JavaTokenType.CHARACTER;

/**
 *
 *
 * <h1>JavaCharacterToken</h1>
 *
 * <p>Java character tokens.
 */
public class JavaCharacterToken extends JavaToken {
  /**
   * Constructor.
   *
   * @param source the source from where to fetch the token's characters.
   * @throws Exception if an error occurred.
   */
  public JavaCharacterToken(Source source) throws Exception {
    super(source);
  }

  /**
   * Extract a Java character token from the source.
   *
   * @throws Exception if an error occurred.
   */
  protected void extract() throws Exception {
    StringBuilder textBuffer = new StringBuilder();
    StringBuilder valueBuffer = new StringBuilder();

    char currentChar = nextChar(); // consume initial quote
    textBuffer.append('\'');

    // if backslash
    if (currentChar == '\\') {
      textBuffer.append(currentChar);
      currentChar = nextChar(); // consume backslash

      // escape characters
      if (currentChar == '\'') {
        textBuffer.append(currentChar);
        valueBuffer.append(currentChar);
      } else if (currentChar == 'n') {
        textBuffer.append(currentChar);
        valueBuffer.append('\n');
      } else if (currentChar == 't') {
        textBuffer.append(currentChar);
        valueBuffer.append('\t');
      } else if (currentChar == '\"') {
          textBuffer.append(currentChar);
          valueBuffer.append('\"');
      } else if (currentChar == '\\') {
    	  
        textBuffer.append(currentChar);
        valueBuffer.append('\\');
      } else { // invalid backslash character
        type = ERROR;
        value = INVALID_CHARACTER;
        do {
          textBuffer.append(currentChar);
          currentChar = nextChar();
        } while (currentChar != '\'');
        textBuffer.append(currentChar);
        currentChar = nextChar();       
        text = textBuffer.toString();
             
        return;
      }
      currentChar = nextChar(); // consume character
    }

    // regular character
    else {
      textBuffer.append(currentChar);
      valueBuffer.append(currentChar);
      currentChar = nextChar(); // consume character
    }

    // closing quote
    if (currentChar == '\'') {
      nextChar(); // consume final quote
      textBuffer.append('\'');

      type = CHARACTER;
      value = valueBuffer.toString();
    } else {
      type = ERROR;
      value = INVALID_CHARACTER;

      // loop until found closing '
      do {
        currentChar = nextChar();
        textBuffer.append(currentChar);
      } while (currentChar != '\'');
      currentChar = nextChar();
    }

    text = textBuffer.toString();
  }
}
