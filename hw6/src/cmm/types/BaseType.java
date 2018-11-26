package cmm.types;

public abstract class BaseType {
  public abstract String toJasminType();
  public abstract String toJasminInstruction();

  public String load(int index) {
    if (index <= 3)
      return String.format("%sload_%d\n", this.toJasminInstruction(), index);
    return this.toJasminInstruction() + "load " + index + "\n";
  }

  public String store(int index) {
    if (index <= 3)
      return String.format("%sstore_%d\n", this.toJasminInstruction(), index);
    return this.toJasminInstruction() + "store " + index + "\n";
  }
}
