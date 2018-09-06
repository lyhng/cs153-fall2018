package wci.frontend.pascal;

import wci.frontend.Source;
import wci.frontend.Token;

/**
 *
 *
 * <h1>PascalToken</h1>
 *
 * <p>Base class for Pascal token classes.
 *
 * <p>Copyright (c) 2009 by Ronald Mak
 *
 * <p>For instructional purposes only. No warranties.
 */
public class PascalToken extends Token {
  /**
   * Constructor.
   *
   * @param source the source from where to fetch the token's characters.
   * @throws Exception if an error occurred.
   */
  protected PascalToken(Source source) throws Exception {
    super(source);
  }
}
