package cmm.symtab;

import cmm.types.BaseType;

public class Symbol {
  public enum SymbolKind {
    DECLARATION,
    PARAMETER,
    FUNCTION
  }

  private SymbolKind kind;
  private BaseType type;
  private SymbolTable symbolTable;
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

  private Symbol(SymbolKind kind, SymbolTable symbolTable) {
    this.kind = kind;
    this.symbolTable = symbolTable;
  }

  public static Symbol createDeclaration(BaseType type) {
    return new Symbol(SymbolKind.DECLARATION, type);
  }

  public static Symbol createParameter(BaseType type, int index) {
    return new Symbol(SymbolKind.PARAMETER, type, index);
  }

  public static Symbol createFunction(SymbolTable symbolTable) {
    return new Symbol(SymbolKind.FUNCTION, symbolTable);
  }

  public SymbolTable getSymbolTable() {
    return symbolTable;
  }

  public void setSymbolTable(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
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
}
