package cmm;

import java.util.function.Function;

public class Test {
  static void test(int a, int b) {
    int c = b / 2;

    System.out.printf("%d %d %d\n", a, b, c);
  }
  public static void main(String[] args) {

    for (int i = 0; i < 100; i++) {
      test(i, 100-i);
    }
  }
}
