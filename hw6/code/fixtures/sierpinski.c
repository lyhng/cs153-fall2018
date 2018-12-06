
void curve(int order, int length, int angle) {
  if (order == 0) {
    forward(length);
  } else {
    curve(order - 1, length / 2, -angle);
    right(angle);
    curve(order - 1, length / 2, +angle);
    right(angle);
    curve(order - 1, length / 2, -angle);
  }
}

void sierpinski(int order, int length) {
  if ((order & 1) == 0) {
    curve(order, length, 60);
  } else {
    right(60);
    curve(order, length, -60);
  }
}

void main() {
  int i;
  start_logo();
  animate();
  penup();
  right(180);
  forward(240);
  left(90);
  forward(140);
  left(90);
  pendown();
  sierpinski(8, 600);
}
