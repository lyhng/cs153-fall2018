void polygon(int size, int num) {
  int angle = 360 / num;
  int i = 0;
  while (i < num) {
    forward(size);
    right(angle);

    i = i + 1;
  }
  return;
}

void main() {
  start_logo();
  animate();

  int i, j;
  int angle = 8;

  i = 3;
  while (i <= 9) {
    if (i != 7) {
      j = 1;
      while (j <= 360 / angle) {
        right(angle);
        polygon(130, i);
        j = j + 1;
      }
    }
    i = i + 1;
  }
}
