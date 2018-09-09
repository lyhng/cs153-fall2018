package wci.frontend.java;

import wci.frontend.EofToken;
import wci.frontend.Scanner;
import wci.frontend.Source;
import wci.frontend.Token;
import wci.frontend.java.tokens.*;

import static wci.frontend.Source.EOF;
import static wci.frontend.Source.EOL;
import static wci.frontend.java.JavaErrorCode.INVALID_CHARACTER;

/**
 *
 *
 * <h1>JavaScanner</h1>
 *
 * <p>The Java scanner.
 *
 * <p>Copyright (c) 2009 by Ronald Mak
 *
 * <p>For instructional purposes only. No warranties.
 */
public class JavaScanner extends Scanner {
  /**
   * Constructor
   *
   * @param source the source to be used with this scanner.
   */
  public JavaScanner(Source source) {
    super(source);
  }

  /**
   * Extract and return the next Java token from the source.
   *
   * @return the next token.
   * @throws Exception if an error occurred.
   */
  protected Token extractToken() throws Exception {
    skipWhiteSpace();

    Token token;
    char currentChar = currentChar();

    // Construct the next token. The current character determines the
    // token type.
    if (currentChar == EOF) {
      token = new EofToken(source);
    } else if (Character.isLetter(currentChar) || currentChar == '_' || currentChar == '$') {
      token = new JavaWordToken(source);
    } else if (Character.isDigit(currentChar)) {
      token = new JavaNumberToken(source);
    } else if (currentChar == '\'') {
      token = new JavaStringToken(source);
    } else if (JavaTokenType.SPECIAL_SYMBOLS.containsKey(Character.toString(currentChar))) {
      token = new JavaSpecialSymbolToken(source);
    } else {
      token = new JavaErrorToken(source, INVALID_CHARACTER, Character.toString(currentChar));
      nextChar(); // consume character
    }

    return token;
  }

  /**
   * Skip whitespace characters by consuming them. A comment is whitespace.
   *
   * @throws Exception if an error occurred.
   */
  private void skipWhiteSpace() throws Exception {
    char currentChar = currentChar();

    while (Character.isWhitespace(currentChar) || (currentChar == '/')) {
      // Start of a comment?
      if (currentChar == '/') {
        currentChar = nextChar();

        // start of * comment
        if (currentChar == '*') {
          while (true) {
            currentChar = nextChar();

            // if found */ sequence break from loop
            if (currentChar == '*' && source.peekChar() == '/') break;
          }

          // Found closing '*'?
          if (currentChar == '*') {
            currentChar = nextChar(); // consume the '*'
            currentChar = nextChar(); // consume the '/'
          }
        }

        // start of double back slash comment
        else if (currentChar == '/') {
          do {
            currentChar = nextChar(); // consume comment characters
          } while (currentChar != EOL);
        }

      }
      // Not a comment.
      else {
        currentChar = nextChar(); // consume whitespace character
      }
    }
  }
}
