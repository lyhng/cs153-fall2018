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

    @Override
    int getLevel() {
      return 0;
    }

    @Override
    public String toString() {
      return "int";
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

    @Override
    int getLevel() {
      return 1;
    }

    @Override
    public String toString() {
      return "long";
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

    @Override
    int getLevel() {
      return 2;
    }

    @Override
    public String toString() {
      return "float";
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

    @Override
    int getLevel() {
      return 3;
    }

    @Override
    public String toString() {
      return "double";
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

    @Override
    int getLevel() {
      return -1;
    }

    @Override
    public String toString() {
      return "char";
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

    @Override
    int getLevel() {
      return -1;
    }

    @Override
    public String toString() {
      return "void";
    }
  }
  
  public static class BooleanType extends BaseType {
	  @Override
	  public String toJasminType() {
		  return "B";
	  }
	  
	  @Override 
	  public String toJasminInstruction() {
		  return "b";
	  }
	  
	  @Override
	  int getLevel() {
		  return -1;
	  }
	  
	  @Override
	  public String toString() {
		  return "bool";
	  }  
  }
}
