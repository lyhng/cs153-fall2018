
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

    int testShr = b >> 3;
    int expectedShr = 2;
    print(testShr);
    print(expectedShr);

    int testXor = a ^ 3;
    int expectedXor = 2;
    print(testXor);
    print(expectedXor);

    int testOr = b | 4;
    int expectedOr = 20;
    print(testOr);
    print(expectedOr);

    int testPriority = a | b ^ a & b << a + b * (b / a);
    int expectedPriority = 17;
    print(testPriority);
    print(expectedPriority);

    int testPriority2 = a >> (b ^ a) | b << a + b - (b / a);
    int expectedPriority2 = 32;
    print(testPriority2);
    print(expectedPriority2);


    int testPriority3 = a | (b ^ a) & b << a * b - (b << a);
    int expectedPriority3 = 1;
    print(testPriority3);
    print(expectedPriority3);

    print(c == 64);

    if (c % 2 == 0) {
        print(99);
    } else {
        print(88);
    }
}

