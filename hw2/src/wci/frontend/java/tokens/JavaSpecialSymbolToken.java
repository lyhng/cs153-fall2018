package wci.frontend.java.tokens;

import wci.frontend.Source;
import wci.frontend.java.JavaToken;

import static wci.frontend.java.JavaErrorCode.INVALID_CHARACTER;
import static wci.frontend.java.JavaTokenType.ERROR;
import static wci.frontend.java.JavaTokenType.SPECIAL_SYMBOLS;

/**
 *
 *
 * <h1>JavaSpecialSymbolToken</h1>
 *
 * <p>Java special symbol tokens.
 *
 * <p>Copyright (c) 2009 by Ronald Mak
 *
 * <p>For instructional purposes only. No warranties.
 */
public class JavaSpecialSymbolToken extends JavaToken {
  /**
   * Constructor.
   *
   * @param source the source from where to fetch the token's characters.
   * @throws Exception if an error occurred.
   */
  public JavaSpecialSymbolToken(Source source) throws Exception {
    super(source);
  }

  /**
   * Extract a Java special symbol token from the source.
   *
   * @throws Exception if an error occurred.
   */
  protected void extract() throws Exception {
    char currentChar = currentChar();

    text = Character.toString(currentChar);
    type = null;

    switch (currentChar) {

        // Single-character special symbols.
      case '+':
      case '-':
      case '*':
      case '/':
      case ',':
      case ';':
      case '\'':
      case '=':
      case '(':
      case ')':
      case '[':
      case ']':
      case '{':
      case '}':
      case '^':
        {
          nextChar(); // consume character
          break;
        }

        // : or :=
      case ':':
        {
          currentChar = nextChar(); // consume ':';

          if (currentChar == '=') {
            text += currentChar;
            nextChar(); // consume '='
          }

          break;
        }

        // < or <= or <>
      case '<':
        {
          currentChar = nextChar(); // consume '<';

          if (currentChar == '=') {
            text += currentChar;
            nextChar(); // consume '='
          } else if (currentChar == '>') {
            text += currentChar;
            nextChar(); // consume '>'
          }

          break;
        }

        // > or >=
      case '>':
        {
          currentChar = nextChar(); // consume '>';

          if (currentChar == '=') {
            text += currentChar;
            nextChar(); // consume '='
          }

          break;
        }

        // . or ..
      case '.':
        {
          currentChar = nextChar(); // consume '.';

          if (currentChar == '.') {
            text += currentChar;
            nextChar(); // consume '.'
          }

          break;
        }

      default:
        {
          nextChar(); // consume bad character
          type = ERROR;
          value = INVALID_CHARACTER;
        }
    }

    // Set the type if it wasn't an error.
    if (type == null) {
      type = SPECIAL_SYMBOLS.get(text);
    }
  }
}
