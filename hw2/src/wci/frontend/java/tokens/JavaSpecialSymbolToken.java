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

  /** All characters that can be a special symbol. */
  private static String ALL_SPECIAL_CHARACTERS = "~!@%^&*-+=|/:;?<>.,'\"()[]{}";

  /** Special characters that may be followed by an equal sign ("="). */
  private static String FOLLOWED_BY_EQUAL = "<>!%^&*-+=|/";

  /** Special characters that may repeat it self once. */
  private static String REPEATABLE_CHARACTERS = "&+-=|/<>";

  /**
   * Extract a Java special symbol token from the source.
   *
   * @throws Exception if an error occurred.
   */
  protected void extract() throws Exception {
    char currentChar = currentChar();
    text = Character.toString(currentChar);
    type = null;

    if (ALL_SPECIAL_CHARACTERS.contains(text)) {
      nextChar();

      if (FOLLOWED_BY_EQUAL.contains(text) && currentChar() == '=') {
        text += currentChar();
        nextChar();
      } else if (REPEATABLE_CHARACTERS.contains(text) && currentChar() == currentChar) {
        text += currentChar();
        nextChar();

        // >>= and <<=
        if ((currentChar == '<' || currentChar == '>') && currentChar() == '=') {
          text += currentChar();
          nextChar();
        }
      } else if (currentChar == '/' && currentChar() == '*'
          || currentChar == '*' && currentChar() == '/') {
        // /* and */
        text += currentChar();
        nextChar();
      }

      type = SPECIAL_SYMBOLS.get(text);
    } else {
      nextChar();
      type = ERROR;
      value = INVALID_CHARACTER;
    }
  }
}
