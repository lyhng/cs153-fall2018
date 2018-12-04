package cmm.types;


import cmm.LabelAssigner;
import cmm.error.IllegalInstruction;

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

  private void ensureInstructionType(String allowed, String message) throws IllegalInstruction {
    if(allowed.contains(this.toJasminInstruction())) {
      return;
    }

    throw new IllegalInstruction(message);
  }

  public String load(int index) {
    if (index <= 3)
      return instr("load", "_%d", index);
    return instr("load", " %d", index);
  }

  public String store(int index) throws IllegalInstruction {
    ensureInstructionType("ilfda", "Invalid variable type");

    if (index <= 3)
      return instr("store", "_%d", index);
    return instr("store", " %d", index);
  }

  public String mul() throws IllegalInstruction {
    ensureInstructionType("ilfd", "Invalid operator type");

    return instr("mul");
  }

  public String div() throws IllegalInstruction {
    ensureInstructionType("ilfd", "Invalid operator type");

    return instr("div");
  }

  public String rem() throws IllegalInstruction {
    ensureInstructionType("ilfd", "Invalid operator type");

    return instr("rem");
  }

  public String add() throws IllegalInstruction {
    ensureInstructionType("ilfd", "Invalid operator type");

    return instr("add");
  }

  public String sub() throws IllegalInstruction {
    ensureInstructionType("ilfd", "Invalid operator type");

    return instr("sub");
  }

  public String return_() { // throws IllegalInstruction {
//    ensureInstructionType("ilfda ", "Invalid operator type");
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

  public String shl() throws IllegalInstruction {
    ensureInstructionType("il", "Invalid operator type");
    return instr("shl");
  }

  public String shr() throws IllegalInstruction {
    ensureInstructionType("il", "Invalid operator type");
    return instr("shr");
  }

  public String and() throws IllegalInstruction {
    ensureInstructionType("il", "Invalid operator type");
    return instr("and");
  }

  public String xor() throws IllegalInstruction {
    ensureInstructionType("il", "Invalid operator type");
    return instr("xor");
  }

  public String or() throws IllegalInstruction {
    ensureInstructionType("il", "Invalid operator type");
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
