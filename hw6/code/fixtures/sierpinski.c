
void curve(int order, int length, int angle) {
  print(order);
  space();
  print(length);
  space();
  print(angle);
  println();

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
  print(order);
  space();
  print(length);
  space();
  print(order & 1);
  println();
  if ((order & 1) == 0) {
    print(1234);
    curve(order, length, 60);
  } else {
    right(60);
    curve(order, length, -60);
  }
}

void main() {
  int i;
  start_logo();
  penup();
  right(180);
  forward(240);
  left(90);
  forward(140);
  left(90);
  pendown();
  sierpinski(8, 600);
}
