package cmm;

import cmm.antlr_gen.CmmLexer;
import cmm.antlr_gen.CmmParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class CmmCompiler {
  public static void main(String[] args) throws Exception {
    String inputFile = null;
    if (args.length > 0) inputFile = args[0];

    InputStream is = System.in;
    if (inputFile != null) is = new FileInputStream(inputFile);

    ANTLRInputStream input = new ANTLRInputStream(is);
    CmmLexer lexer = new CmmLexer(input);

    CommonTokenStream tokens = new CommonTokenStream(lexer);

    System.out.println("Tokens: ");
    tokens.fill();

    for (Token token: tokens.getTokens()) {
      System.out.println(token.toString());
    }

    CmmParser parser = new CmmParser(tokens);
    ParseTree tree = parser.program();

    System.out.println("\nParse tree (Lisp format):");
    System.out.println(tree.toStringTree(parser));
  }
}
