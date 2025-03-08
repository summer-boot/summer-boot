package io.github.summer.boot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要
 *
 * @author changebooks@qq.com
 */
public final class MsgDigest {
    /**
     * MD5摘要器
     */
    private static final AbstractDigest MD5_DIGEST = new AbstractDigest() {
        /**
         * 算法名
         */
        private static final String ALGORITHM = "MD5";

        @Override
        public String getAlgorithm() {
            return ALGORITHM;
        }

    };

    /**
     * SHA1摘要器
     */
    private static final AbstractDigest SHA1_DIGEST = new AbstractDigest() {
        /**
         * 算法名
         */
        private static final String ALGORITHM = "SHA1";

        @Override
        public String getAlgorithm() {
            return ALGORITHM;
        }

    };

    /**
     * SHA-256摘要器
     */
    private static final AbstractDigest SHA256_DIGEST = new AbstractDigest() {
        /**
         * 算法名
         */
        private static final String ALGORITHM = "SHA-256";

        @Override
        public String getAlgorithm() {
            return ALGORITHM;
        }

    };

    /**
     * 加密
     *
     * @param value 明文
     * @return 16进制密文
     */
    public static String md5(String value) {
        return MD5_DIGEST.toHex(value);
    }

    /**
     * 加密
     *
     * @param value 明文
     * @return 16进制密文
     */
    public static String sha1(String value) {
        return SHA1_DIGEST.toHex(value);
    }

    /**
     * 加密
     *
     * @param value 明文
     * @return 16进制密文
     */
    public static String sha256(String value) {
        return SHA256_DIGEST.toHex(value);
    }

    /**
     * 消息摘要基类
     */
    public abstract static class AbstractDigest {

        private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDigest.class);

        /**
         * 摘要计算器
         */
        private final MessageDigest calculator;

        public AbstractDigest() {
            String algorithm = getAlgorithm();
            AssertUtils.nonEmpty(algorithm, "algorithm");

            try {
                this.calculator = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("unsupported algorithm, algorithm: {}, throwable: ", algorithm, ex);
                throw new RuntimeException(ex);
            }
        }

        /**
         * 加密字符串
         *
         * @param value 明文
         * @return 16进制密文
         */
        public String toHex(String value) {
            return toHex(value != null ? value.getBytes() : null);
        }

        /**
         * 加密字节数组
         *
         * @param value 明文
         * @return 16进制密文
         */
        public String toHex(byte[] value) {
            byte[] data = toBytes(value);
            return TypeCast.toHex(data);
        }

        /**
         * 加密字节数组
         *
         * @param value 明文
         * @return 密文
         */
        public byte[] toBytes(byte[] value) {
            return calculator.digest(value);
        }

        /**
         * 摘要算法
         *
         * @return 算法名，如：MD5、SHA1、SHA-256
         */
        public abstract String getAlgorithm();

        public MessageDigest getCalculator() {
            return calculator;
        }

    }

}
