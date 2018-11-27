package cmm_runtime;

public class CmmRuntime {
  public static void print(int x) {
    System.out.println(x);
  }

  public static void print_double(double x){
    System.out.println(x);
  }

  public static double sqrt(int n) {
    return Math.sqrt((float)n);
  }
}
