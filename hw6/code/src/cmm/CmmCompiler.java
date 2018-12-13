package cmm;

import cmm.antlr_gen.CmmLexer;
import cmm.antlr_gen.CmmParser;
import cmm.error.BaseError;
import cmm.symtab.SymbolTable;
import jasmin.Main;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;
import java.util.List;


public class CmmCompiler {
  private static void showErrors(String[] lines, List<BaseError> errors) {
    System.err.println("Compilation Error:\n");
    errors.stream()
        .map((err) -> err.toReadableMessage(lines))
        .forEach(System.err::println);
  }

  public static void main(String[] args) throws Exception {
    String inputFile = null;
    if (args.length > 0) inputFile = args[0];

    InputStream is = System.in;
    if (inputFile != null) is = new FileInputStream(inputFile);

    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    String[] lines = reader.lines().toArray(String[]::new);
    String source = String.join("\n", lines);
    CharStream input = CharStreams.fromString(source);
    CmmLexer lexer = new CmmLexer(input);

    CommonTokenStream tokens = new CommonTokenStream(lexer);

    System.out.println("Tokens: ");
    tokens.fill();

    for (Token token : tokens.getTokens()) {
      System.out.println(token.toString());
    }

    CmmParser parser = new CmmParser(tokens);
    ParseTree tree = parser.cmm();

    System.out.println("\nParse tree (Lisp format):");
    System.out.println(tree.toStringTree(parser));

    PrintWriter out;
    String outputFileName = "out.j";
    try {
      out = new PrintWriter(new FileWriter(outputFileName));
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

    List<BaseError> errors = exprTypeVisitor.getErrors();
    if (!errors.isEmpty()) {
      showErrors(lines, errors);
      out.close();
      return;
    }

    DirectCompiler compiler = new DirectCompiler(table);
    String result = compiler.visit(tree);

    errors = compiler.getErrors();
    if (!errors.isEmpty()) {
      showErrors(lines, errors);
      out.close();
      return;
    }

    out.write(result);

    System.out.println("\n\nCompile Result:");
    System.out.println(result);

    out.close();

    // Assemble the output file to class file
    Main jasmin = new Main();
    jasmin.assemble(outputFileName);
  }
}
