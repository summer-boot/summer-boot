package io.github.summer.boot.util;

/**
 * 加解密
 *
 * @author changebooks@qq.com
 */
public final class Cipher {
    /**
     * byte size
     */
    private static final int BYTE_SIZE = (-Byte.MIN_VALUE) + Byte.MAX_VALUE + 1;

    private Cipher() {
    }

    /**
     * 加密
     *
     * @param value plain text
     * @param key   crypto key
     * @param len   salt size
     * @return cipher text
     */
    public static String encode(String value, String key, int len) {
        String salt = StringUtils.random(len);
        String saltedKey = saltKey(key, salt);

        String text = Cipher.encode(value, saltedKey);
        return salt + text;
    }

    /**
     * 解密
     *
     * @param value cipher text
     * @param key   crypto key
     * @param len   salt size
     * @return plain text
     */
    public static String decode(String value, String key, int len) {
        String salt = value.substring(0, len);
        String text = value.substring(len);

        String saltedKey = saltKey(key, salt);
        return Cipher.decode(text, saltedKey);
    }

    /**
     * 加盐
     *
     * @param key  raw key
     * @param salt salt text
     * @return salted key
     */
    public static String saltKey(String key, String salt) {
        return MsgDigest.sha256(key + MsgDigest.sha256(salt + key));
    }

    /**
     * 加密
     *
     * @param value plain text
     * @param key   crypto key
     * @return cipher text
     */
    public static String encode(String value, String key) {
        byte[] plainText = value.getBytes();
        byte[] cipherText = compute(plainText, key);
        return UrlSafeBase64.encode(cipherText);
    }

    /**
     * 解密
     *
     * @param value cipher text
     * @param key   crypto key
     * @return plain text
     */
    public static String decode(String value, String key) {
        byte[] cipherText = UrlSafeBase64.decode(value);
        byte[] plainText = compute(cipherText, key);
        return new String(plainText);
    }

    /**
     * xor bit operate
     *
     * @param value plain text
     * @param key   crypto key
     * @return cipher text
     */
    public static byte[] compute(byte[] value, String key) {
        byte[] k = key.getBytes();
        byte[] iv = iv(k);
        return doCompute(value, iv);
    }

    /**
     * xor bit operate
     * c = a ^ b
     * a = b ^ c
     * b = a ^ c
     *
     * @param value a byte array
     * @param iv    a iv array
     * @return a operated byte array
     */
    public static byte[] doCompute(byte[] value, byte[] iv) {
        byte[] r = new byte[value.length];
        int len = iv.length;

        for (int i = 0, j = 0, k = 0; i < value.length; i++) {
            j = (j + 1) % len;
            k = (k + abs(iv[j])) % len;
            swap(iv, j, k);

            int a = abs(value[i]);
            int b = abs(iv[(abs(iv[j]) + abs(iv[k])) % BYTE_SIZE]);
            int c = a ^ b;

            r[i] = (byte) c;
        }

        return r;
    }

    /**
     * initialization vector
     *
     * @param value a byte array
     * @return a iv array
     */
    public static byte[] iv(byte[] value) {
        byte[] v = new byte[BYTE_SIZE];
        int len = value.length;

        for (int i = 0; i < BYTE_SIZE; i++) {
            v[i] = value[i % len];
        }

        return doIv(v);
    }

    /**
     * initialization vector
     *
     * @param value a byte array
     * @return a iv array
     */
    public static byte[] doIv(byte[] value) {
        byte[] r = range();
        int len = value.length;

        for (int i = 0, j = 0; i < len; i++) {
            j = (j + abs(r[i]) + abs(value[i])) % len;
            swap(r, i, j);
        }

        return r;
    }

    /**
     * range array
     *
     * @return a byte array from 0 to 255
     */
    public static byte[] range() {
        byte[] r = new byte[BYTE_SIZE];

        for (int i = 0; i < BYTE_SIZE; i++) {
            r[i] = (byte) i;
        }

        return r;
    }

    /**
     * swap value
     *
     * @param v a byte array
     * @param i index1
     * @param j index2
     */
    public static void swap(byte[] v, int i, int j) {
        byte b = v[i];
        v[i] = v[j];
        v[j] = b;
    }

    /**
     * absolute value
     *
     * @param b a byte value
     * @return (b) : (b + 256)
     */
    public static int abs(byte b) {
        return (b > 0) ? b : (b + BYTE_SIZE);
    }

}
