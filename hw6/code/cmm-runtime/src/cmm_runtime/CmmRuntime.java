package cmm_runtime;


public class CmmRuntime {
  public static void print(int x) {
    System.out.println(x);
  }

  public static void space() {
    System.out.println(" ");
  }

  public static void println() {
    System.out.println();
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
    if (turtleGraphics != null) turtleGraphics.refresh();

    long current = System.currentTimeMillis();
    long duration = current - CmmRuntime.start;

    System.out.printf("Total run time: %dms\n", duration);
  }

  /* LOGO */

  private static TurtleGraphics turtleGraphics;

  public static void start_logo() {
    turtleGraphics = new TurtleGraphics();
    turtleGraphics.start();
    turtleGraphics.show_frame();
  }  // initialize the GUI canvas here. We can ask the user to run this before using any of the following commands

  public static void animate() {
    turtleGraphics.setAnimated();
  }

  public static void forward(int distance) {
    turtleGraphics.move(distance);
    refresh();
  }  // move the turtle by distance

  public static void right(int angle) {
    turtleGraphics.rotate(-angle);
    refresh();
  } // clockwise rotating

  public static void left(int angle) {
    turtleGraphics.rotate(angle);
    refresh();
  }  // counter-clockwise rotating

  public static void square(int length) {
    turtleGraphics.drawSquare(length);
    refresh();
  }  // draw a square with the current coordinate as the left bottom corner of the square

  private static void refresh() {
    turtleGraphics.refresh();
    if (turtleGraphics.isAnimated()) {
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
      }
    }
  }

  public static void wait(int seconds) {
    try {
      Thread.sleep(seconds);
    }
    catch (InterruptedException e) {
      // TODO: Handle this
    }
  }  // Thread.sleep

  public static void clearscreen() {
    turtleGraphics.clear();
    turtleGraphics.resetTurtle();
    refresh();
  }  // reset, clear the screen and set turtle to origin.

  public static void penup() {
    turtleGraphics.setPen(false);
    refresh();
  }    // stop leaving trace on route, i.e. disable drawing only move the turtle

  public static void pendown() {
    turtleGraphics.setPen(true);
    refresh();
  }   // start leaving trace on route, i.e. enable drawing

  public static void hideturtle() {
    turtleGraphics.setTurtleVisible(false);
    refresh();
  }  // make the turtle invisible

  public static void showturtle() {
    turtleGraphics.setTurtleVisible(true);
    refresh();
  } // make the turtle visible

  public static void home() {
    turtleGraphics.resetTurtle();
    refresh();
  } // set turtle to origin, but not clear the screen
}
