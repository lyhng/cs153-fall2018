package cmm.symtab;

import cmm.types.BaseType;
import cmm.types.FunctionType;
import cmm.types.TypeFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Symbol {

  private SymbolKind kind;
  private BaseType type;
  private int index;

  private Symbol(SymbolKind kind, BaseType type) {
    this.kind = kind;
    this.type = type;
  }

  private Symbol(SymbolKind kind, BaseType type, int index) {
    this.kind = kind;
    this.type = type;
    this.index = index;
  }

  public static Symbol createDeclaration(BaseType type) {
    return new Symbol(SymbolKind.DECLARATION, type);
  }

  public static Symbol createParameter(BaseType type, int index) {
    return new Symbol(SymbolKind.PARAMETER, type, index);
  }

  public static Symbol createFunction(SymbolTable symbolTable, BaseType returnType) {
    return new Symbol(SymbolKind.DECLARATION, TypeFactory.createFunction(symbolTable, returnType));
  }

  public SymbolKind getKind() {
    return kind;
  }

  public BaseType getType() {
    return type;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String load() {
    return this.getType().load(this.index);
  }

  public String store() {
    return this.getType().store(this.index);
  }

  public static Symbol fromMethod(Method method, SymbolTable parent) {
    String name = method.getName();
    SymbolTable table = new SymbolTable(name, parent);
    Parameter[] parameters = method.getParameters();

    int index = 0;
    for (Parameter parameter: parameters) {
      BaseType type = TypeFactory.fromJava(parameter.getType());
      table.put(parameter.getName(), Symbol.createParameter(type, index++));
    }

    return Symbol.createFunction(table, TypeFactory.fromJava(method.getReturnType()));
  }

  public String getName() {
    if (this.type instanceof FunctionType) {
      return ((FunctionType)this.type).getSymbolTable().getName();
    }
    return "";
  }

  public enum SymbolKind {
    DECLARATION,
    PARAMETER
  }
}
