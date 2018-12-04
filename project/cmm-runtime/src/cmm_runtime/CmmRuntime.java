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

  private static long start = 0;

  public static void _start_timer() {
    CmmRuntime.start = System.currentTimeMillis();
  }

  public static void _show_timer() {
    long current = System.currentTimeMillis();
    long duration = current - CmmRuntime.start;

    System.out.printf("Total run time: %dms\n", duration);
  }
}
