package ru.sstu.nlp;

public class Task6 {

    enum State {
        START,          // Начальное состояние
        IN_DIGIT,       // В процессе чтения цифры
        NOT_DIGIT       // Читаем не-цифру
    }

    public static String onlyDigests(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        var currentState = State.START;
        var result = new StringBuilder();

        // Проходим по всем символам строки
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // В зависимости от текущего состояния обрабатываем символ
            switch (currentState) {
                case START -> {
                    if (Character.isDigit(currentChar)) {
                        result.append(currentChar);
                        currentState = State.IN_DIGIT;
                    } else {
                        currentState = State.NOT_DIGIT;
                    }
                }

                case IN_DIGIT -> {
                    if (Character.isDigit(currentChar)) {
                        // Продолжаем читать цифру
                        result.append(currentChar);
                    } else {
                        // Цифры закончились
                        currentState = State.NOT_DIGIT;
                    }
                }

                case NOT_DIGIT -> {
                    if (Character.isDigit(currentChar)) {
                        // Началась новая цифра
                        result.append(currentChar);
                        currentState = State.IN_DIGIT;
                    }
                }
            }
        }

        return result.toString();
    }

    static void main() {
        String[] strings = {
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

        for (String str : strings) {
            String result = onlyDigests(str);
            System.out.println("'" + str + "' -> '" + result + "'");
        }
    }
}