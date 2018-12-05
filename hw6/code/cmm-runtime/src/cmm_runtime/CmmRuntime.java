package cmm_runtime;


import java.awt.geom.Rectangle2D;

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

  /* LOGO */

  private static TurtleGraphics turtleGraphics = new TurtleGraphics();

  static void start_logo() {
    turtleGraphics.start();
  }  // initialize the GUI canvas here. We can ask the user to run this before using any of the following commands


  static void forward(int distance) {
    turtleGraphics.move(distance);
  }  // move the turtle by distance

  static void right(int angle) {
    turtleGraphics.rotate(-angle);
  } // clockwise rotating

  static void left(int angle) {
    turtleGraphics.rotate(angle);
  }  // counter-clockwise rotating

  static void square(int length) {
    turtleGraphics.drawSquare(length);
  }  // draw a square with the current coordinate as the left bottom corner of the square

  static void wait(int seconds) {
    try {
      Thread.sleep(seconds);
    }
    catch (InterruptedException e) {
      // TODO: Handle this
    }
  }  // Thread.sleep

  static void clearscreen() {
    turtleGraphics.clear();
    turtleGraphics.resetTurtle();
  }  // reset, clear the screen and set turtle to origin.

  static void penup() {
    turtleGraphics.setPen(false);
  }    // stop leaving trace on route, i.e. disable drawing only move the turtle

  static void pendown() {
    turtleGraphics.setPen(true);
  }   // start leaving trace on route, i.e. enable drawing

  static void hideturtle() {
    turtleGraphics.setTurtleVisible(false);
  }  // make the turtle invisible

  static void showturtle() {
    turtleGraphics.setTurtleVisible(true);
  } // make the turtle visible


  static void home() {
    turtleGraphics.resetTurtle();
  } // set turtle to origin, but not clear the screen
}
