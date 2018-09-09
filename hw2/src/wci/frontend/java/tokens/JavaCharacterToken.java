package wci.frontend.java.tokens;

import wci.frontend.Source;
import wci.frontend.java.JavaToken;

import static wci.frontend.Source.EOF;
import static wci.frontend.java.JavaErrorCode.UNEXPECTED_EOF;
import static wci.frontend.java.JavaTokenType.ERROR;
import static wci.frontend.java.JavaTokenType.CHARACTER;

/**
 *
 * <p>Java character tokens.
 * 
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

    if (currentChar == '\\') { //Consume escape char
      textBuffer.append(currentChar);
      currentChar = nextChar();
      if (currentChar == 'n' || currentChar == 't') { //Handle special chars
        textBuffer.append(currentChar);
        valueBuffer.append("\\" + currentChar);
      }
      else { // interpret next char literally
        textBuffer.append(currentChar);
        valueBuffer.append(currentChar);
      }
    }
    else { //Normal case no escape character
      textBuffer.append(currentChar);
      valueBuffer.append(currentChar);
    }
    
    currentChar = nextChar();
    if (currentChar == '\'') {
      nextChar(); // consume final quote
      textBuffer.append('\'');

      type = CHARACTER;
      value = valueBuffer.toString();
    } else {
      type = ERROR;
      value = UNEXPECTED_EOF;
    }

    text = textBuffer.toString();
  }
}
