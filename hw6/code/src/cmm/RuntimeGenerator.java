package cmm;

import cmm.symtab.Symbol;
import cmm.symtab.SymbolTable;
import cmm.types.FunctionType;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class RuntimeGenerator {
  public static SymbolTable generate() {
    try {
      return generate("cmm_runtime.CmmRuntime");
    } catch (ClassNotFoundException e) {
      System.err.println("WARNING: Runtime library not found!");
      return new SymbolTable("");
    }
  }

  public static SymbolTable generate(String name) throws ClassNotFoundException {
    Class runtime = Class.forName(name);
    SymbolTable root = new SymbolTable(runtime.getCanonicalName());
    Arrays.stream(runtime.getDeclaredMethods())
        .filter(
            method ->
                Modifier.isPublic(method.getModifiers())
                    && Modifier.isStatic(method.getModifiers()))
        .map(method -> Symbol.fromMethod(method, root))
        .forEach(symbol -> root.put(symbol.getName(), symbol));

    return root;
  }

  public static void main(String[] args) throws ClassNotFoundException {
    SymbolTable a = generate();

    FunctionType func = (FunctionType)a.get("print").getType();

    a.pprint();
    System.out.println(func.buildSignature());
  }
}
