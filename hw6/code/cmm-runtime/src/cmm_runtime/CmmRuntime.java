package cmm_runtime;


import java.awt.geom.Rectangle2D;

public class CmmRuntime {
  public static void print(int x) {
    System.out.print(x);
  }

  public static void space() {
    System.out.print(" ");
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
    long current = System.currentTimeMillis();
    long duration = current - CmmRuntime.start;

    turtleGraphics.show_frame();

    System.out.printf("Total run time: %dms\n", duration);
  }

  /* LOGO */

  private static TurtleGraphics turtleGraphics = new TurtleGraphics();

  public static void start_logo() {
    turtleGraphics.start();
  }  // initialize the GUI canvas here. We can ask the user to run this before using any of the following commands


  public static void forward(int distance) {
    turtleGraphics.move(distance);
  }  // move the turtle by distance

  public static void right(int angle) {
    turtleGraphics.rotate(-angle);
  } // clockwise rotating

  public static void left(int angle) {
    turtleGraphics.rotate(angle);
  }  // counter-clockwise rotating

  public static void square(int length) {
    turtleGraphics.drawSquare(length);
  }  // draw a square with the current coordinate as the left bottom corner of the square

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
  }  // reset, clear the screen and set turtle to origin.

  public static void penup() {
    turtleGraphics.setPen(false);
  }    // stop leaving trace on route, i.e. disable drawing only move the turtle

  public static void pendown() {
    turtleGraphics.setPen(true);
  }   // start leaving trace on route, i.e. enable drawing

  public static void hideturtle() {
    turtleGraphics.setTurtleVisible(false);
  }  // make the turtle invisible

  public static void showturtle() {
    turtleGraphics.setTurtleVisible(true);
  } // make the turtle visible

  public static void home() {
    turtleGraphics.resetTurtle();
  } // set turtle to origin, but not clear the screen

  static void curve(int order, int length, int angle) {
    System.out.printf("%d %d %d\n", order ,length, angle);
    if (order == 0) {
      forward(length);
    } else {
      curve(order - 1, length / 2, -angle);
      right(angle);
      curve(order - 1, length / 2, +angle);
      right(angle);
      curve(order - 1, length / 2, - angle);
    }
  }

  static void sierpinski(int order, int length) {
    System.out.printf("%d %d %d\n", order, length, (order & 1));
    if ((order & 1) == 0) {
      curve(order, length, +60);
    } else {
      right(+60);
      curve(order, length, -60);
    }
  }

  public static void main(String[] args) {
    start_logo();
    penup();
    right(180);
    forward(240);
    left(90);
    forward(140);
    left(90);
    pendown();
    sierpinski(1, 600);
    turtleGraphics.show_frame();
  }
}
