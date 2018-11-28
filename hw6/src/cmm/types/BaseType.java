package cmm.types;


import cmm.LabelAssigner;

import java.util.Arrays;

public abstract class BaseType {
  public abstract String toJasminType();

  public abstract String toJasminInstruction();

  private String instr(String op) {
    return instr(op, "");
  }

  private String instr(String op, String format, Object... args) {
    Object[] out = new Object[]{this.toJasminInstruction(), op};
    out = Arrays.copyOf(out, 2 + args.length);
    System.arraycopy(args, 0, out, 2, args.length);

    return String.format("%s%s" + format + "\n", out);
  }

  public String load(int index) {
    if (index <= 3)
      return instr("load", "_%d", index);
    return instr("load", " %d", index);
  }

  public String store(int index) {
    if (index <= 3)
      return instr("store", "_%d", index);
    return instr("store", " %d", index);
  }

  public String mul() {
    return instr("mul");
  }

  public String div() {
    return instr("div");
  }

  public String rem() {
    return instr("rem");
  }

  public String add() {
    return instr("add");
  }

  public String sub() {
    return instr("sub");
  }

  public String return_() {
    return instr("return");
  }

  abstract int getLevel();

  public boolean canCastTo(BaseType o) throws Exception {
    int level_a = this.getLevel();
    int level_b = o.getLevel();

    if (level_a == level_b) return true;

    if (level_a != -1 && level_b != -1) {
      return level_a < level_b;
    }

    throw new Exception();
  }

  public BaseType tryCastTo(BaseType o) throws Exception {
    return this.canCastTo(o) ? o : this;
  }

  public String shl() {
    return instr("shl");
  }

  public String shr() {
    return instr("shr");
  }

  public String and() {
    return instr("and");
  }

  public String xor() {
    return instr("xor");
  }

  public String or() {
    return instr("or");
  }

  private String relational(String op) {
    String label = LabelAssigner.getInstance().getLabel();
    String end = LabelAssigner.getInstance().getLabel();

    return "if_icmp" + op + " " + label + "\n"
        + "iconst_1\n"
        + "goto " + end + "\n"
        + label + ": \n"
        + "iconst_0\n"
        + end + ": \n"
        +"nop\n";
  }

  public String gt() {
    return relational("le");
  }

  public String ge() {
    return relational("lt");
  }

  public String lt() {
    return relational("ge");
  }

  public String le() {
    return relational("gt");
  }

  public String eq() {
    return relational("ne");
  }

  public String ne() {
    return relational("eq");
  }
}
