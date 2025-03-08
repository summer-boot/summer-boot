package io.github.summer.boot.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要
 *
 * @author changebooks@qq.com
 */
public final class HmacSha {
    /**
     * 算法
     */
    private static final String ALGORITHM = "HmacSHA256";

    /**
     * MAC
     */
    private final Mac encoder;

    public HmacSha(String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);

        encoder = Mac.getInstance(ALGORITHM);
        encoder.init(keySpec);
    }

    /**
     * 摘要
     *
     * @param value 明文
     * @return 16进制密文
     */
    public String digest(String value) {
        byte[] data = value.getBytes();
        byte[] result = encoder.doFinal(data);
        return TypeCast.toHex(result);
    }

}
