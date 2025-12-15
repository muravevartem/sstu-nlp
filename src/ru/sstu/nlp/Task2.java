package ru.sstu.nlp;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Частоты слов
 */
public class Task2 {


    /*
     Введите строку:
     Вышел ежик из тумана, вынул ножик из кармана ...
     -------------------------------------
     Частота слов:
     ножик : 1
     кармана : 1
     тумана : 1
     ежик : 1
     Вышел : 1
     вынул : 1
     из : 2
     */
    public static void main(String[] args) {
        System.out.println("Введите строку:");
        String str = new Scanner(System.in).nextLine();

        Map<String, Integer> frequency = getWordFrequency(str);

        System.out.println("Частота слов:");
        for (var entry : frequency.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    private static Map<String, Integer> getWordFrequency(String str) {
        if (str == null || str.isEmpty()) {
            return Map.of();
        }

        var map = new HashMap<String, Integer>();
        var word = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                word.append(ch);
            } else if (!word.isEmpty()) {
                map.merge(word.toString(), 1, Integer::sum);
                word.setLength(0);
            }
        }
        if (!word.isEmpty()) {
            map.merge(word.toString(), 1, Integer::sum);
        }

        return map;
    }
}
