
int add(int a, int b) {
    return a + b;
}

int main() {
    int a = 1;
    int b = 2;
    int c = a + b;
    int d = add(a, b);

    print(c);

    if (a != b) {
        print("error");
    }

    return 0;
}