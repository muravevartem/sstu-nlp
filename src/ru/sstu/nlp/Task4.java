package ru.sstu.nlp;

/*
Шаг 1a: удаление определенных суффиксов множественного числа и причастий прошедшего времени.

Шаг 1b: удаление суффиксов, если корень слова достаточно длинный.

Шаг 1c: замена 'y' на 'i', если в слове есть гласная.

Шаг 2: удаление суффиксов, связанных с частями речи (например, -ational, -izer и т.д.).

Шаг 3: удаление суффиксов, таких как -icate, -ative, -alize и т.д.

Шаг 4: удаление дополнительных суффиксов, таких как -al, -ance, -ic и т.д.

Шаг 5a: удаление окончания 'e', если основа длинная.

Шаг 5b: удаление двойного 'l' в конце слова, если основа длинная.
 */
public class Task4 {
    static void main() {
        String[] testWords = {
                "caresses", "ponies", "ties", "caress", "cats",
                "feed", "agreed", "plastered", "bled", "motoring",
                "sing", "conflated", "troubled", "sized", "hopping",
                "tanned", "falling", "hissing", "fizzed", "failing",
                "filing", "happy", "sky", "organization", "condition"
        };

        System.out.println("Стемминг слов по алгоритму Портера:");
        System.out.println("=====================================");
        for (String word : testWords) {
            System.out.printf("%-15s -> %s%n", word, stem(word));
        }
    }


    /**
     * Проверка - является ли символ согласной
     *
     * @param word  исходное слово
     * @param index индекс символа
     * @return флаг - является ли символ согласной
     */
    private static boolean isConsonant(String word, int index) {
        char c = word.charAt(index);
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            return false;
        }
        if (c == 'y') {
            return (index == 0) || !isConsonant(word, index - 1);
        }
        return true;
    }

    /**
     * Подсчет последовательностей гласных-согласных в слове
     *
     * @param word исходное слово
     * @return количество последовательностей гласных-согласных в слове
     */
    private static int measure(String word) {
        int n = 0;
        int i = 0;
        while (true) {
            if (i >= word.length()) return n;
            if (!isConsonant(word, i)) break;
            i++;
        }
        i++;

        while (true) {
            while (true) {
                if (i >= word.length()) return n;
                if (isConsonant(word, i)) break;
                i++;
            }
            i++;
            n++;
            while (true) {
                if (i >= word.length()) return n;
                if (!isConsonant(word, i)) break;
                i++;
            }
            i++;
        }
    }

    private static boolean containsVowel(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!isConsonant(word, i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean doubleConsonant(String word) {
        if (word.length() < 2) return false;
        char last = word.charAt(word.length() - 1);
        char prev = word.charAt(word.length() - 2);
        return last == prev && isConsonant(word, word.length() - 1);
    }

    private static boolean cvc(String word) {
        if (word.length() < 3) return false;
        int i = word.length() - 3;
        if (isConsonant(word, i) && !isConsonant(word, i + 1) &&
                isConsonant(word, i + 2)) {
            char c = word.charAt(i + 2);
            return c != 'w' && c != 'x' && c != 'y';
        }
        return false;
    }

    public static String stem(String word) {
        if (word.length() <= 2) return word.toLowerCase();

        word = word.toLowerCase();

        // Шаг 1a
        if (word.endsWith("sses") || word.endsWith("ies")) {
            word = word.substring(0, word.length() - 2);
        } else if (word.endsWith("s") && !word.endsWith("ss")) {
            word = word.substring(0, word.length() - 1);
        }

        // Шаг 1b
        boolean step1b = false;
        if (word.endsWith("eed")) {
            if (measure(word.substring(0, word.length() - 3)) > 0) {
                word = word.substring(0, word.length() - 1);
            }
        } else if (word.endsWith("ed")) {
            if (containsVowel(word.substring(0, word.length() - 2))) {
                word = word.substring(0, word.length() - 2);
                step1b = true;
            }
        } else if (word.endsWith("ing")) {
            if (containsVowel(word.substring(0, word.length() - 3))) {
                word = word.substring(0, word.length() - 3);
                step1b = true;
            }
        }

        if (step1b) {
            if (word.endsWith("at") || word.endsWith("bl") || word.endsWith("iz")) {
                word = word + "e";
            } else if (doubleConsonant(word) &&
                    !word.endsWith("l") && !word.endsWith("s") && !word.endsWith("z")) {
                word = word.substring(0, word.length() - 1);
            } else if (measure(word) == 1 && cvc(word)) {
                word = word + "e";
            }
        }

        // Шаг 1c
        if (word.endsWith("y") && containsVowel(word.substring(0, word.length() - 1))) {
            word = word.substring(0, word.length() - 1) + "i";
        }

        // Шаг 2
        String[] step2Suffixes = {
                "ational", "tional", "enci", "anci", "izer", "abli", "alli",
                "entli", "eli", "ousli", "ization", "ation", "ator",
                "alism", "iveness", "fulness", "ousness", "aliti", "iviti",
                "biliti", "logi"
        };

        String[] step2Replacements = {
                "ate", "tion", "ence", "ance", "ize", "able", "al",
                "ent", "e", "ous", "ize", "ate", "ate",
                "al", "ive", "ful", "ous", "al", "ive",
                "ble", "log"
        };

        for (int i = 0; i < step2Suffixes.length; i++) {
            if (word.endsWith(step2Suffixes[i])) {
                String stem = word.substring(0, word.length() - step2Suffixes[i].length());
                if (measure(stem) > 0) {
                    word = stem + step2Replacements[i];
                }
                break;
            }
        }

        // Шаг 3
        String[] step3Suffixes = {
                "icate", "ative", "alize", "iciti", "ical", "ful", "ness"
        };

        String[] step3Replacements = {
                "ic", "", "al", "ic", "ic", "", ""
        };

        for (int i = 0; i < step3Suffixes.length; i++) {
            if (word.endsWith(step3Suffixes[i])) {
                String stem = word.substring(0, word.length() - step3Suffixes[i].length());
                if (measure(stem) > 0) {
                    word = stem + step3Replacements[i];
                }
                break;
            }
        }

        // Шаг 4
        String[] step4Suffixes = {
                "al", "ance", "ence", "er", "ic", "able", "ible", "ant",
                "ement", "ment", "ent", "ion", "ou", "ism", "ate", "iti",
                "ous", "ive", "ize"
        };

        for (String suffix : step4Suffixes) {
            if (word.endsWith(suffix)) {
                String stem = word.substring(0, word.length() - suffix.length());
                if (measure(stem) > 1) {
                    if (suffix.equals("ion")) {
                        char c = stem.charAt(stem.length() - 1);
                        if (c == 's' || c == 't') {
                            word = stem;
                        }
                    } else {
                        word = stem;
                    }
                }
                break;
            }
        }

        // Шаг 5a
        if (word.endsWith("e")) {
            String stem = word.substring(0, word.length() - 1);
            if (measure(stem) > 1) {
                word = stem;
            } else if (measure(stem) == 1 && !cvc(stem)) {
                word = stem;
            }
        }

        // Шаг 5b
        if (measure(word) > 1 && doubleConsonant(word) && word.endsWith("l")) {
            word = word.substring(0, word.length() - 1);
        }

        return word;
    }
}
