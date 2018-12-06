package cmm_runtime;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class TurtleGraphics extends JPanel {

  /* Turtle class*/
  private static class Turtle {

    // Length for drawing arrow head
    private static final double ARROW_LENGTH = 20.0f;

    double x, y;
    double angle;
    boolean visible;
    Color color;
    boolean enable;

    Turtle(double x, double y, double angle) {
      this.x = x;
      this.y = y;
      this.angle = angle;
      this.visible = true;
      this.color = Color.BLACK;
      this.enable = true;
    }

    // Compute paths for drawing arrow head
    private Path2D calcDrawPath() {
      // End point
      double endX = this.x;
      double endY = this.y;

      double arrowWidth = ARROW_LENGTH * 0.5f;

      Path2D.Float drawPath = new Path2D.Float();
      drawPath.moveTo ( endX - ARROW_LENGTH, endY - arrowWidth );
      drawPath.lineTo ( endX, endY );
      drawPath.lineTo ( endX - ARROW_LENGTH, endY + arrowWidth );
      drawPath.lineTo ( endX - ARROW_LENGTH * 0.6f, endY );
      drawPath.lineTo ( endX - ARROW_LENGTH, endY - arrowWidth );

      return drawPath;
    }

    void draw(Graphics2D g) {
      g.fill ( calcDrawPath() );
    }
  }

  private static final int CANVAS_WIDTH = 800;
  private static final int CANVAS_HEIGHT = 800;

  private Turtle turtle;
  private ArrayList<Shape> scene;

  private JFrame frame;
  private boolean started;

  private boolean animated;

  public TurtleGraphics() {
    this.scene = new ArrayList<>();
    this.turtle = new Turtle(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2, 0);
    this.frame = new JFrame("Turtle Graphics");
    this.started = false;
  }

  public boolean isStarted() {
    return started;
  }

  public boolean isAnimated() {
    return animated;
  }

  public void setAnimated() {
    this.animated = true;
  }


  public void start() {
    this.started = true;
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
    frame.setLocationByPlatform(true);
    this.setSize(frame.getSize());
    this.setOpaque(true);
    this.setBackground(Color.WHITE);
    frame.getContentPane().add(BorderLayout.CENTER, this);
  }

  public void show_frame() {
    frame.setVisible(true);
  }

  public void setPen(boolean penStatus) {
    turtle.enable = penStatus;
  }

  public void setTurtleVisible(boolean isVisible) {
    this.turtle.visible = isVisible;
  }

  public void drawSquare(int length) {
    Rectangle2D rec = new Rectangle2D.Double(turtle.x, turtle.y - length, length, length);
    if (turtle.enable)
      scene.add(rec);
  }

  // Assuming angle is in degrees
  public void rotate(int angle) {
    double rad = angle * (Math.PI / 180);
    turtle.angle += rad;
    turtle.angle %= 2 * Math.PI;
  }

  public void move(int distance) {
    double dx = 1 * Math.cos(-turtle.angle);
    double dy = 1 * Math.sin(-turtle.angle);
    Line2D.Double line = new Line2D.Double();
    line.x1 = turtle.x;
    line.y1 = turtle.y;
    line.x2 = line.x1 + dx * distance;
    line.y2 = line.y1 + dy * distance;
    turtle.x = line.x2;
    turtle.y = line.y2;
    if (turtle.enable)
      scene.add(line);
  }

  public void clear() {
    scene.clear();
  }

  public void resetTurtle() {
    turtle.x = CANVAS_WIDTH / 2;
    turtle.y = CANVAS_HEIGHT / 2;
    turtle.angle = 0;
  }

  public void refresh() {
    this.validate();
    this.repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;

    g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setBackground(Color.WHITE);
    g2d.setColor(turtle.color);


    // Hack to draw arrow head:
    // Rotate canvas -> draw turtle -> rotate back
    g2d.rotate(-turtle.angle, turtle.x, turtle.y);
    if (turtle.visible) turtle.draw(g2d);
    g2d.rotate(turtle.angle, turtle.x, turtle.y);

    // Draw scene
    try {
      for (Shape s : scene) {
        g2d.draw(s);
      }
    } catch (ConcurrentModificationException ignored) {
    }
  }
}

