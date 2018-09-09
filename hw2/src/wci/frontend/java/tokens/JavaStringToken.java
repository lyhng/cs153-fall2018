package wci.frontend.java.tokens;

import wci.frontend.Source;
import wci.frontend.java.JavaToken;

import static wci.frontend.Source.EOF;
import static wci.frontend.java.JavaErrorCode.INVALID_CHARACTER;
import static wci.frontend.java.JavaErrorCode.UNEXPECTED_EOF;
import static wci.frontend.java.JavaTokenType.ERROR;
import static wci.frontend.java.JavaTokenType.STRING;
import static wci.frontend.java.tokens.JavaCharacterToken.ESCAPE_CHAR;
import static wci.frontend.java.tokens.JavaCharacterToken.ESCAPE_CHARACTERS;

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
    textBuffer.append('"');

    // Get string characters.
    do {
      // Replace any whitespace character with a blank.
      if (Character.isWhitespace(currentChar)) {
        currentChar = ' ';
      }

      // escape characters
      if (currentChar == '\\') {
        textBuffer.append(currentChar);
        currentChar = nextChar();

        if (ESCAPE_CHARACTERS.contains(Character.toString(currentChar))) {
          textBuffer.append(currentChar);
          valueBuffer.append(ESCAPE_CHAR[ESCAPE_CHARACTERS.indexOf(currentChar)]);
        } else { // invalid escape character
          type = ERROR;
          value = INVALID_CHARACTER;
          do {
            textBuffer.append(currentChar);
            currentChar = nextChar();
          } while (currentChar != '"');
          textBuffer.append(currentChar);
          currentChar = nextChar();
          text = textBuffer.toString();
          return;
        }
        currentChar = nextChar();
      }

      // regular chacater
      else if ((currentChar != '"') && (currentChar != EOF)) {
        textBuffer.append(currentChar);
        valueBuffer.append(currentChar);
        currentChar = nextChar(); // consume character
      }

    } while ((currentChar != '"') && (currentChar != EOF));

    // closing quote
    if (currentChar == '"') {
      nextChar(); // consume final quote
      textBuffer.append('"');

      type = STRING;
      value = valueBuffer.toString();
    } else {
      type = ERROR;
      value = UNEXPECTED_EOF;
    }

    text = textBuffer.toString();
  }
}
