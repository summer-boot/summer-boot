package io.github.summer.boot.util;

import io.github.summer.boot.base.Assert;
import io.github.summer.boot.base.Check;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 进制转换
 * 字符串 to 数字
 * 数字 to 字符串
 *
 * @author changebooks@qq.com
 */
public final class BaseConverter {
    /**
     * 转换32进制
     */
    public static final AbstractConverter BASE32 = new AbstractConverter() {
        /**
         * 32进制，8数字 + 24小写字母，排除：0、1、o、l
         */
        private static final String ALPHABET = "23456789abcdefghijkmnpqrstuvwxyz";

        @Override
        public int getRemainder(long num) {
            return (int) (num & getAlphabetCharacterLenMask());
        }

        @Override
        public String getAlphabet() {
            return ALPHABET;
        }

    };

    /**
     * 转换62进制
     */
    public static final AbstractConverter BASE62 = new AbstractConverter() {
        /**
         * 62进制，数字 + 小写字母 + 大写字母
         */
        private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        @Override
        public int getRemainder(long num) {
            return (int) (num - (num / getAlphabetCharacterLen()) * getAlphabetCharacterLen());
        }

        @Override
        public String getAlphabet() {
            return ALPHABET;
        }

    };

    /**
     * 32进制 to 10进制
     *
     * @param s 字符串，if s = "" return 0;
     * @return 数字（gt 0）
     */
    public static long convert32To10(String s) {
        return BASE32.toLong(s);
    }

    /**
     * 10进制 to 32进制
     * 线程不安全
     *
     * @param num 数字（gt 0），if num le 0 return "";
     * @return 字符串
     */
    public static String convert10To32(long num) {
        return BASE32.fromLong(num);
    }

    /**
     * 62进制 to 10进制
     *
     * @param s 字符串，if s = "" return 0;
     * @return 数字（gt 0）
     */
    public static long convert62To10(String s) {
        return BASE62.toLong(s);
    }

    /**
     * 10进制 to 62进制
     * 线程不安全
     *
     * @param num 数字（gt 0），if num le 0 return "";
     * @return 字符串
     */
    public static String convert10To62(long num) {
        return BASE62.fromLong(num);
    }

    /**
     * 进制转换基类
     */
    public abstract static class AbstractConverter {
        /**
         * 非法字符
         */
        public static final String ILLEGAL_CHAR = "illegal character %c in %s";

        /**
         * 字符数组
         */
        private final char[] alphabetCharacter;

        /**
         * 字符数组长度
         */
        private final int alphabetCharacterLen;

        /**
         * 字符长度掩码
         */
        private final int alphabetCharacterLenMask;

        /**
         * 字符 : 下标
         */
        private final Map<Character, Integer> alphabetIndex = new HashMap<>();

        /**
         * 字符下标Map长度
         */
        private final int alphabetIndexSize;

        public AbstractConverter() {
            String alphabet = getAlphabet();
            AssertUtils.nonEmpty(alphabet, "alphabet");

            this.alphabetCharacter = alphabet.toCharArray();
            this.alphabetCharacterLen = this.alphabetCharacter.length;
            this.alphabetCharacterLenMask = this.alphabetCharacterLen - 1;

            for (int i = 0; i < this.alphabetCharacterLen; i++) {
                this.alphabetIndex.put(this.alphabetCharacter[i], i);
            }

            this.alphabetIndexSize = this.alphabetIndex.size();
        }

        /**
         * 字符串 to 数字
         *
         * @param s 字符串，if s = "" return 0;
         * @return 数字（ge 0）
         */
        public long toLong(String s) {
            if (Check.isEmpty(s)) {
                return 0;
            }

            char[] value = s.toCharArray();
            int len = value.length;
            char c;
            Integer index;

            long r = 0;
            for (int i = 0, j = len - 1; i < len; i++, j--) {
                c = value[i];
                index = alphabetIndex.get(c);
                Assert.checkArgument(Check.nonNull(index), String.format(ILLEGAL_CHAR, c, s));

                r += (long) (index * (Math.pow(alphabetIndexSize, j)));
            }

            return r;
        }

        /**
         * 数字 to 字符串
         * 线程不安全
         *
         * @param num 数字（gt 0），if num le 0 return "";
         * @return 字符串
         */
        public String fromLong(long num) {
            if (num <= 0) {
                return "";
            }

            Stack<Character> stack = new Stack<>();
            int i;
            while (num > 0) {
                i = getRemainder(num);
                stack.add(alphabetCharacter[i]);

                num /= alphabetCharacterLen;
            }

            StringBuilder r = new StringBuilder();
            while (!stack.isEmpty()) {
                r.append(stack.pop());
            }

            return r.toString();
        }

        /**
         * 求余数
         *
         * @param num 数字（gt 0）
         * @return 余数
         */
        public abstract int getRemainder(long num);

        /**
         * 字符串
         *
         * @return 数字和小写字母
         */
        public abstract String getAlphabet();

        public char[] getAlphabetCharacter() {
            return alphabetCharacter;
        }

        public int getAlphabetCharacterLen() {
            return alphabetCharacterLen;
        }

        public int getAlphabetCharacterLenMask() {
            return alphabetCharacterLenMask;
        }

        public Map<Character, Integer> getAlphabetIndex() {
            return alphabetIndex;
        }

        public int getAlphabetIndexSize() {
            return alphabetIndexSize;
        }

    }

}
