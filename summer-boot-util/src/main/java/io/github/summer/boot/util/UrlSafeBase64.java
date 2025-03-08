package io.github.summer.boot.util;

import java.util.Base64;

/**
 * URL安全的Base64加解密类
 * 去掉和还原密文中的+、/、=
 *
 * @author changebooks@qq.com
 */
public final class UrlSafeBase64 {
    /**
     * 填充密文，长度 % 4 = 0
     */
    private static final String EQUAL_SIGN_4 = "====";

    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    private static final Base64.Decoder DECODER = Base64.getDecoder();

    private UrlSafeBase64() {
    }

    /**
     * 解密
     *
     * @param value 密文
     * @return 明文，字节数组，或null
     */
    public static byte[] decode(String value) {
        String result = urlDecode(value);
        if (result != null) {
            return DECODER.decode(result);
        } else {
            return null;
        }
    }

    /**
     * 加密
     *
     * @param value 明文
     * @return 密文，字符串，或null
     */
    public static String encode(byte[] value) {
        String result = ENCODER.encodeToString(value);
        if (result != null) {
            return urlEncode(result);
        } else {
            return null;
        }
    }

    /**
     * 转义URL
     * 还原密文中的+、/、=
     *
     * @param value 待转义字符串
     * @return 转义后字符串，或null
     */
    public static String urlDecode(String value) {
        if (value == null) {
            return null;
        }

        int size = value.length() % 4;
        // 增加 (4 - size) 个 "="
        String padding = (size > 0) ? EQUAL_SIGN_4.substring(size) : "";

        String result = value + padding;
        return result
                .replace('-', '+')
                .replace('_', '/');
    }

    /**
     * 转义URL
     * 去掉密文中的+、/、=
     *
     * @param value 待转义字符串
     * @return 转义后字符串，或null
     */
    public static String urlEncode(String value) {
        if (value != null) {
            return value
                    .replace('+', '-')
                    .replace('/', '_')
                    .replace("=", "");
        } else {
            return null;
        }
    }

}
