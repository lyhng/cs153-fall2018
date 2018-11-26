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
  private BaseType returnType;
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

  private Symbol(SymbolKind kind, SymbolTable symbolTable, BaseType returnType) {
    this.kind = kind;
    this.symbolTable = symbolTable;
    this.returnType = returnType;
  }

  public static Symbol createDeclaration(BaseType type) {
    return new Symbol(SymbolKind.DECLARATION, type);
  }

  public static Symbol createParameter(BaseType type, int index) {
    return new Symbol(SymbolKind.PARAMETER, type, index);
  }

  public static Symbol createFunction(SymbolTable symbolTable, BaseType returnType) {
    return new Symbol(SymbolKind.FUNCTION, symbolTable, returnType);
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

  public String buildSignature() {
    if (this.kind == SymbolKind.FUNCTION) {
      StringBuilder builder = new StringBuilder();

      builder.append(this.symbolTable.getName()).append('(');

      for (Symbol symbol: this.symbolTable.values()) {
        if (symbol.getKind() == SymbolKind.PARAMETER) {
          builder.append(symbol.getType().toJasminType());
        }
      }

      builder.append(')');
      builder.append(this.returnType.toJasminType());

      return builder.toString();
    }
    return "";
  }

  public String load() {
    return this.getType().load(this.index);
  }

  public String store() {
    return this.getType().store(this.index);
  }
}
