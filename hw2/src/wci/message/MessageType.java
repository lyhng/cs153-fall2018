package wci.message;

/**
 *
 *
 * <h1>MessageType</h1>
 *
 * <p>Message types.
 *
 * <p>Copyright (c) 2009 by Ronald Mak
 *
 * <p>For instructional purposes only. No warranties.
 */
public enum MessageType {
  SOURCE_LINE,
  SYNTAX_ERROR,
  PARSER_SUMMARY,
  INTERPRETER_SUMMARY,
  COMPILER_SUMMARY,
  MISCELLANEOUS,
  TOKEN,
  ASSIGN,
  FETCH,
  BREAKPOINT,
  RUNTIME_ERROR,
  CALL,
  RETURN,
}