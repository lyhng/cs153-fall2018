package cmm;

import cmm.antlr_gen.CmmLexer;
import cmm.antlr_gen.CmmParser;
import cmm.symtab.SymbolTable;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

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
    ParseTree tree = parser.cmm();

    System.out.println("\nParse tree (Lisp format):");
    System.out.println(tree.toStringTree(parser));

    PrintWriter out;
    try {
      out = new PrintWriter(new FileWriter("out.j"));
    } catch (Exception ex) {
      ex.printStackTrace();
      return;
    }

    SymbolTableVisitor symbolVisitor = new SymbolTableVisitor();
    symbolVisitor.visit(tree);

    System.out.println("\n");
    SymbolTable table = symbolVisitor.getStack();
    table.pprint();

    ExpressionTypeVisitor exprTypeVisitor = new ExpressionTypeVisitor(table);
    exprTypeVisitor.visit(tree);

    DirectCompiler compiler = new DirectCompiler(table);
    String result = compiler.visit(tree);
    out.write(result);

    System.out.println("\n\nCompile Result:");
    System.out.println(result);

    out.close();
  }
}
