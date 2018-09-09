package wci.frontend.java;

import wci.frontend.TokenType;

import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 *
 * <h1>JavaTokenType</h1>
 *
 * <p>Java token types.
 *
 * <p>Copyright (c) 2009 by Ronald Mak
 *
 * <p>For instructional purposes only. No warranties.
 */
public enum JavaTokenType implements TokenType {
  // Java Reserve Words
  ABSTRACT,
  DOUBLE,
  INT,
  SUPER,
  BREAK,
  ELSE,
  LONG,
  SWITCH,
  CASE,
  ENUM,
  NATIVE,
  CHAR,
  EXTENDS,
  RETURN,
  THIS,
  CLASS,
  FLOAT,
  SHORT,
  THROW,
  CONST,
  FOR,
  PACKAGE,
  VOID,
  CONTINUE,
  GOTO,
  PROTECTED,
  VOLATILE,
  DO,
  IF,
  STATIC,
  WHILE,

  // Special symbols.
  PLUS("+"),
  MINUS("-"),
  STAR("*"),
  SLASH("/"),
  COLON_EQUALS(":="),
  DOT("."),
  COMMA(","),
  SEMICOLON(";"),
  COLON(":"),
  QUOTE("'"),
  EQUALS("="),
  NOT_EQUALS("<>"),
  LESS_THAN("<"),
  LESS_EQUALS("<="),
  GREATER_EQUALS(">="),
  GREATER_THAN(">"),
  LEFT_PAREN("("),
  RIGHT_PAREN(")"),
  LEFT_BRACKET("["),
  RIGHT_BRACKET("]"),
  LEFT_BRACE("{"),
  RIGHT_BRACE("}"),
  UP_ARROW("^"),
  DOT_DOT(".."),

  IDENTIFIER,
  INTEGER,
  REAL,
  STRING,
  ERROR,
  END_OF_FILE;

  private static final int FIRST_RESERVED_INDEX = ABSTRACT.ordinal();
  private static final int LAST_RESERVED_INDEX = WHILE.ordinal();

  private static final int FIRST_SPECIAL_INDEX = PLUS.ordinal();
  private static final int LAST_SPECIAL_INDEX = DOT_DOT.ordinal();
  // Set of lower-cased Java reserved word text strings.
  public static HashSet<String> RESERVED_WORDS = new HashSet<String>();
  // Hash table of Java special symbols.  Each special symbol's text
  // is the key to its Java token type.
  public static Hashtable<String, JavaTokenType> SPECIAL_SYMBOLS =
      new Hashtable<String, JavaTokenType>();

  static {
    JavaTokenType values[] = JavaTokenType.values();
    for (int i = FIRST_RESERVED_INDEX; i <= LAST_RESERVED_INDEX; ++i) {
      RESERVED_WORDS.add(values[i].getText().toLowerCase());
    }
  }

  static {
    JavaTokenType values[] = JavaTokenType.values();
    for (int i = FIRST_SPECIAL_INDEX; i <= LAST_SPECIAL_INDEX; ++i) {
      SPECIAL_SYMBOLS.put(values[i].getText(), values[i]);
    }
  }

  private String text; // token text

  /** Constructor. */
  JavaTokenType() {
    this.text = this.toString().toLowerCase();
  }

  /**
   * Constructor.
   *
   * @param text the token text.
   */
  JavaTokenType(String text) {
    this.text = text;
  }

  /**
   * Getter.
   *
   * @return the token text.
   */
  public String getText() {
    return text;
  }
}
