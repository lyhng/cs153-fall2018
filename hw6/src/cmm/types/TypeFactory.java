package cmm.types;

public class TypeFactory {
  private static BaseType INT_TYPE = new PrimitiveType.IntType();
  private static BaseType LONG_TYPE = new PrimitiveType.LongType();
  private static BaseType FLOAT_TYPE = new PrimitiveType.FloatType();
  private static BaseType DOUBLE_TYPE = new PrimitiveType.DoubleType();
  private static BaseType CHAR_TYPE = new PrimitiveType.DoubleType();
  private static BaseType VOID_TYPE = new PrimitiveType.VoidType();

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
}
