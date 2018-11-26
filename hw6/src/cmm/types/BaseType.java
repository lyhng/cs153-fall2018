package cmm.types;

public abstract class BaseType {
  public abstract String toJasminType();
  public abstract String toJasminInstruction();

  public String load(int local) {
    return this.toJasminInstruction() + "load " + local;
  }
}
