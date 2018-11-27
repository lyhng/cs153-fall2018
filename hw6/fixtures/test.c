
int add(int a, int b) {
    return a + b;
}

void main() {
    int a = 1;
    int b = 2 * 8;
    int c = b << 2;

    print(c << 2);
    print(c);
    print((c + 1) & 1);
}

