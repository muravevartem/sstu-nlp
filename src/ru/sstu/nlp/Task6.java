package ru.sstu.nlp;

import java.util.Scanner;

public class Task6 {
    static void main() {
        System.out.println("Введите число:");
        int n = new Scanner(System.in).nextInt();

        System.out.println("!" + n + " = " + factorial(n));
    }

    private static long factorial(int n) {
        State state = State.START;

        long result = 1;
        int counter = n;

        while (true) {
            System.out.println("Состояние: " + state);
            switch (state) {
                case START:
                    if (n == 0 || n == 1) {
                        result = 1;
                        state = State.END;
                    } else {
                        state = State.CALCULATING;
                    }
                    break;

                case CALCULATING:
                    result *= counter;
                    counter--;

                    if (counter <= 1) {
                        state = State.END;
                    }
                    break;

                case END:
                    return result;
            }
        }
    }

    enum State {
        START,
        CALCULATING,
        END,
    }
}
