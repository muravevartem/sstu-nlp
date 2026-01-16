package ru.sstu.nlp;

public class Task6 {

    enum State {
        START,          // Начальное состояние
        IN_DIGIT,       // В процессе чтения цифры
        NOT_DIGIT       // Читаем не-цифру
    }

    public static String filterDigits(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        State currentState = State.START;
        StringBuilder result = new StringBuilder();

        // Проходим по всем символам строки
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // В зависимости от текущего состояния обрабатываем символ
            switch (currentState) {
                case START:
                    if (Character.isDigit(currentChar)) {
                        // Первая цифра в последовательности
                        result.append(currentChar);
                        currentState = State.IN_DIGIT;
                        System.out.println("START -> IN_DIGIT: '" + currentChar + "'");
                    } else {
                        // Не цифра
                        currentState = State.NOT_DIGIT;
                        System.out.println("START -> NOT_DIGIT: '" + currentChar + "'");
                    }
                    break;

                case IN_DIGIT:
                    if (Character.isDigit(currentChar)) {
                        // Продолжаем читать цифру
                        result.append(currentChar);
                        System.out.println("IN_DIGIT -> IN_DIGIT: '" + currentChar + "'");
                    } else {
                        // Цифры закончились
                        currentState = State.NOT_DIGIT;
                        System.out.println("IN_DIGIT -> NOT_DIGIT: '" + currentChar + "'");
                    }
                    break;

                case NOT_DIGIT:
                    if (Character.isDigit(currentChar)) {
                        // Началась новая цифра
                        result.append(currentChar);
                        currentState = State.IN_DIGIT;
                        System.out.println("NOT_DIGIT -> IN_DIGIT: '" + currentChar + "'");
                    } else {
                        // Продолжаем читать не-цифры
                        System.out.println("NOT_DIGIT -> NOT_DIGIT: '" + currentChar + "'");
                    }
                    break;
            }
        }

        return result.toString();
    }

    static void main() {
        String[] testCases = {
                "abc123def456",     // → "123456"
                "Hello123World",    // → "123"
                "123-456-7890",     // → "1234567890"
                "No digits here!",  // → ""
                "987",              // → "987"
                "a1b2c3d4e5",       // → "12345"
                "",                 // → ""
                "  123  abc 456 ",  // → "123456"
                "!@#$%^&*()",       // → ""
                "0a1b2c3",          // → "0123"
                "2024 год"          // → "2024"
        };

        for (String testCase : testCases) {
            String result = filterDigits(testCase);
            System.out.printf("Вход:  \"%s\"\n", testCase);
            System.out.printf("Выход: \"%s\"\n", result);
            System.out.println("-".repeat(40));
        }
    }
}