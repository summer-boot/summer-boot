package io.github.summer.boot.util;

import io.github.summer.boot.base.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 字母求和
 *
 * @author changebooks@qq.com
 */
public final class AlphabetSum {
    /**
     * 26字母表
     */
    private static final Map<Character, Integer> ALPHABET = new HashMap<>();

    static {
        ALPHABET.put('a', 1);
        ALPHABET.put('b', 2);
        ALPHABET.put('c', 3);
        ALPHABET.put('d', 4);
        ALPHABET.put('e', 5);
        ALPHABET.put('f', 6);
        ALPHABET.put('g', 7);
        ALPHABET.put('h', 8);
        ALPHABET.put('i', 9);
        ALPHABET.put('j', 10);
        ALPHABET.put('k', 11);
        ALPHABET.put('l', 12);
        ALPHABET.put('m', 13);
        ALPHABET.put('n', 14);
        ALPHABET.put('o', 15);
        ALPHABET.put('p', 16);
        ALPHABET.put('q', 17);
        ALPHABET.put('r', 18);
        ALPHABET.put('s', 19);
        ALPHABET.put('t', 20);
        ALPHABET.put('u', 21);
        ALPHABET.put('v', 22);
        ALPHABET.put('w', 23);
        ALPHABET.put('x', 24);
        ALPHABET.put('y', 25);
        ALPHABET.put('z', 26);
    }

    /**
     * 求和
     *
     * @param word the word
     * @return characters sum
     */
    public static int sum(String word) {
        if (Check.isEmpty(word)) {
            return 0;
        }

        char[] letter = word.toLowerCase().toCharArray();

        int totalNum = 0;
        for (char c : letter) {
            if (ALPHABET.containsKey(c)) {
                int num = ALPHABET.get(c);
                totalNum += num;
            }
        }

        return totalNum;
    }

}
