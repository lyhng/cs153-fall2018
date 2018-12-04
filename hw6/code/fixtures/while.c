
void main() {
    int counter = 0;

    while (counter < 10) {
        print(10 - counter);
        counter = counter + 1;
    }

    counter = 0;

    while (counter < 0) {
        print(10 - counter);
        counter = counter + 1;
    }

    print(99);

    counter = 0;

    do {
        print(10 - counter);
        counter = counter + 1;
    } while (counter < 0);
}