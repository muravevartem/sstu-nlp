package ru.sstu.nlp;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Частоты букв
 */
public class Task1 {
    public static void main(String[] args) {
        System.out.println("Введите строку:");
        String str = new Scanner(System.in).nextLine();

        Map<Character, Integer> frequency = getCharFrequency(str);

        System.out.println("Частота символов:");
        for (var entry : frequency.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    private static Map<Character, Integer> getCharFrequency(String str) {
        if (str == null || str.isEmpty()) {
            return Map.of();
        }

        var map = new HashMap<Character, Integer>();
        for (char c : str.toCharArray()) {
            char lower = Character.toLowerCase(c);
            map.put(lower, map.getOrDefault(lower, 0) + 1);
        }
        return map;
    }
}
