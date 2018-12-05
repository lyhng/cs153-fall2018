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
  int i, j;

  i = 3;
  while (i <= 5) {
    j = 4;
    while (j <= 360) {
      right(4);
      polygon(200, i);
      j = j + 1;
    }
    i = i + 1;
  }
}
