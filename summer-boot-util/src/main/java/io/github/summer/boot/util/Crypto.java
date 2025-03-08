package io.github.summer.boot.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 加解密
 * SELECT AES_DECRYPT(FROM_BASE64('value'), 'key') FROM dual;
 * SELECT TO_BASE64(AES_ENCRYPT('value', 'key')) FROM dual;
 *
 * @author changebooks@qq.com
 */
public final class Crypto {
    /**
     * 算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * BASE64
     */
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    /**
     * AES
     */
    private final Cipher encoder;
    private final Cipher decoder;

    private Crypto(String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);

        encoder = Cipher.getInstance(ALGORITHM);
        encoder.init(Cipher.ENCRYPT_MODE, keySpec);

        decoder = Cipher.getInstance(ALGORITHM);
        decoder.init(Cipher.DECRYPT_MODE, keySpec);
    }

    /**
     * 加密
     *
     * @param value plain text
     * @return cipher text
     */
    public String encode(String value) throws BadPaddingException, IllegalBlockSizeException {
        byte[] result = encrypt(value);
        if (result != null) {
            return BASE64_ENCODER.encodeToString(result);
        } else {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param value cipher text
     * @return plain text
     */
    public String decode(String value) throws BadPaddingException, IllegalBlockSizeException {
        byte[] result = BASE64_DECODER.decode(value);
        if (result != null) {
            return decrypt(result);
        } else {
            return null;
        }
    }

    /**
     * 加密
     *
     * @param value plain text
     * @return cipher text
     */
    public byte[] encrypt(String value) throws BadPaddingException, IllegalBlockSizeException {
        return encoder.doFinal(value.getBytes());
    }

    /**
     * 解密
     *
     * @param value cipher text
     * @return plain text
     */
    public String decrypt(byte[] value) throws BadPaddingException, IllegalBlockSizeException {
        byte[] result = decoder.doFinal(value);
        if (result != null) {
            return new String(result);
        } else {
            return null;
        }
    }

}
