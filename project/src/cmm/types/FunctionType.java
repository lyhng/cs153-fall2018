package cmm.types;

import cmm.symtab.Symbol;
import cmm.symtab.SymbolTable;

public class FunctionType extends BaseType {
  private SymbolTable symbolTable;
  private BaseType returnType;

  public FunctionType(SymbolTable symbolTable, BaseType returnType) {
    this.symbolTable = symbolTable;
    this.returnType = returnType;
  }

  @Override
  public String toJasminType() {
    return "";
  }

  @Override
  public String toJasminInstruction() {
    return "";
  }

  @Override
  int getLevel() {
    return -1;
  }

  public SymbolTable getSymbolTable() {
    return symbolTable;
  }

  public BaseType getReturnType() {
    return this.returnType;
  }

  public String buildSignature() {
    StringBuilder builder = new StringBuilder();

    builder.append(this.symbolTable.getName()).append('(');

    for (Symbol symbol: this.symbolTable.values()) {
      if (symbol.getKind() == Symbol.SymbolKind.PARAMETER) {
        builder.append(symbol.getType().toJasminType());
      }
    }

    builder.append(')');
    builder.append(this.returnType.toJasminType());

    return builder.toString();
  }
}
