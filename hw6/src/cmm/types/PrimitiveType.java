package cmm.types;

class PrimitiveType {
  public static class IntType extends BaseType {
    @Override
    public String toJasminType() {
      return "I";
    }

    @Override
    public String toJasminInstruction() {
      return "i";
    }
  }

  public static class LongType extends BaseType {
    @Override
    public String toJasminType() {
      return "J";
    }

    @Override
    public String toJasminInstruction() {
      return "l";
    }
  }

  public static class FloatType extends BaseType {
    @Override
    public String toJasminType() {
      return "F";
    }

    @Override
    public String toJasminInstruction() {
      return "f";
    }
  }

  public static class DoubleType extends BaseType {
    @Override
    public String toJasminType() {
      return "D";
    }

    @Override
    public String toJasminInstruction() {
      return "d";
    }
  }

  public static class CharType extends BaseType {
    @Override
    public String toJasminType() {
      return "C";
    }

    @Override
    public String toJasminInstruction() {
      return "c";
    }
  }

  public static class VoidType extends BaseType {
    @Override
    public String toJasminType() {
      return "V";
    }

    @Override
    public String toJasminInstruction() {
      // TODO: verify this or maybe throw an Exception
      return "v";
    }
  }
}
