import org.antlr.v4.runtime.*;

public class Main {
  public static void main(String[] args) throws Exception {

    CharStream input = new ANTLRFileStream("test.cmm");
    CmmLexer lexer = new CmmLexer(input);

    // print tokens
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    System.out.println("Tokens: ");
    tokens.fill();
    for (Token token : tokens.getTokens()) {
      System.out.println(token.toString());
    }
    System.out.println("\n");

    // print parse tree

    System.out.println("Parse Tree (Lisp Format):");
    CmmParser parser = new CmmParser(tokens);
    System.out.println(parser.program().toStringTree(parser));
  }
}
