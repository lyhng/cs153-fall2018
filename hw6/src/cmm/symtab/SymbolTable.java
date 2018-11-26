package cmm.symtab;

import java.util.LinkedHashMap;
import java.util.Map;

public class SymbolTable extends LinkedHashMap<String, Symbol> {
  private String name;
  private SymbolTable previous;
  private int local;
  private int level;

  public SymbolTable(String name, SymbolTable previous) {
    this.local = 0;
    this.level = 0;

    this.name = name;
    this.previous = previous;

    if (this.previous != null) {
      this.level = this.previous.getLevel() + 1;
    }
  }

  public String getName() {
    return name;
  }

  public SymbolTable() {
    this(null, null);
  }

  public SymbolTable getPrevious() {
    return previous;
  }

  @Override
  public Symbol put(String key, Symbol value) {
    if (value.getKind() == Symbol.SymbolKind.DECLARATION) {
      value.setIndex(this.local++);
    }

    return super.put(key, value);
  }

  public void pprint() {
    this.pprint(0);
  }

  private void pprint(int level) {
    String space = new String(new char[level]).replace("\0", "│ ");
    String name = this.name == null ? "" : this.name;

    if (level == 0) {
      System.out.printf("%s┌──── Symbol Table ────────────────────────────┐\n", space);
    } else {
      System.out.printf("%s┌──── Symbol Table - %-10s (level: %-1d) ────┐\n", space, name, level);
    }

    for (Map.Entry<String, Symbol> entry : this.entrySet()) {
      Symbol symbol = entry.getValue();
      System.out.printf("%s| %-10s: %-12s ", space, entry.getKey(), symbol.getKind());

      if (symbol.getKind() == Symbol.SymbolKind.FUNCTION) {
        System.out.printf("%-19s |\n", "");
        symbol.getSymbolTable().pprint(level + 1);
      } else {
        System.out.printf(" %-18s |\n", String.format("(%s, %d)", symbol.getType(), symbol.getIndex()));
      }
    }

    System.out.printf("%s└──────────────────────────────────────────────┘\n", space);
    if (level != 0) System.out.printf("%s%-44s |\n", space, "");
  }

  public int getLevel() {
    return level;
  }
}
