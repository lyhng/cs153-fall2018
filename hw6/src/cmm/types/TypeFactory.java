package cmm.types;

import cmm.symtab.SymbolTable;

public class TypeFactory {
  public static final BaseType INT_TYPE = new PrimitiveType.IntType();
  public static final BaseType LONG_TYPE = new PrimitiveType.LongType();
  public static final BaseType FLOAT_TYPE = new PrimitiveType.FloatType();
  public static final BaseType DOUBLE_TYPE = new PrimitiveType.DoubleType();
  public static final BaseType CHAR_TYPE = new PrimitiveType.CharType();
  public static final BaseType VOID_TYPE = new PrimitiveType.VoidType();

  static BaseType fromString(String typename) {
    switch (typename) {
      case "int":
        return INT_TYPE;
      case "long":
        return LONG_TYPE;
      case "float":
        return FLOAT_TYPE;
      case "double":
        return DOUBLE_TYPE;
      case "char":
        return CHAR_TYPE;
      case "void":
        return VOID_TYPE;
    }
    return null;
  }

  public static BaseType createFunction(SymbolTable symbolTable, BaseType returnType) {
    return new FunctionType(symbolTable, returnType);
  }

  public static BaseType fromJava(Class<?> returnType) {
    if (returnType.equals(int.class)) {
      return INT_TYPE;
    } else if (returnType.equals(long.class)) {
      return LONG_TYPE;
    } else if (returnType.equals(float.class)) {
      return FLOAT_TYPE;
    } else if (returnType.equals(double.class)) {
      return DOUBLE_TYPE;
    } else if (returnType.equals(char.class)) {
      return CHAR_TYPE;
    } else if (returnType.equals(void.class)) {
      return VOID_TYPE;
    }

    return null;
  }
}
