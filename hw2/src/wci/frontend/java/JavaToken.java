package wci.frontend.java;

import wci.frontend.Source;
import wci.frontend.Token;

/**
 *
 *
 * <h1>javaToken</h1>
 *
 * <p>Base class for java token classes.
 *
 * <p>Copyright (c) 2009 by Ronald Mak
 *
 * <p>For instructional purposes only. No warranties.
 */
public class JavaToken extends Token {
  /**
   * Constructor.
   *
   * @param source the source from where to fetch the token's characters.
   * @throws Exception if an error occurred.
   */
  protected JavaToken(Source source) throws Exception {
    super(source);
  }
}
