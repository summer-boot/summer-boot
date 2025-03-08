package io.github.summer.boot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 类型转换，避免NumberFormatException
 *
 * <pre>
 * object to int
 * object to long
 * object to float
 * object to double
 * byte[] to hex
 * byte[] to string
 * string to byte[]
 * </pre>
 *
 * @author changebooks@qq.com
 */
public final class TypeCast {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeCast.class);

    /**
     * 默认的字符编码
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private TypeCast() {
    }

    /**
     * Convert Object to Integer
     * <pre>
     * TypeCast.toInt(null, -1)   = -1
     * TypeCast.toInt("", -1)     = -1
     * TypeCast.toInt(4.56, -1)   = 4
     * TypeCast.toInt("4.56", -1) = 4
     * </pre>
     *
     * @param value        an Object
     * @param defaultValue Value to return if value is null, empty or convert failed.
     * @return Returns the converted value if it exists, or defaultValue
     */
    public static int toInt(Object value, final int defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException ex1) {
            LOGGER.error("toInt convert object to integer failed, value: {}, throwable: ", value, ex1);
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (NumberFormatException ex2) {
                LOGGER.error("toInt convert object to double failed, value: {}, throwable: ", value, ex2);
                return defaultValue;
            }
        }
    }

    /**
     * Convert Object to Long
     * <pre>
     * TypeCast.toLong(null, -1)   = -1
     * TypeCast.toLong("", -1)     = -1
     * TypeCast.toLong(4.56, -1)   = 4
     * TypeCast.toLong("4.56", -1) = 4
     * </pre>
     *
     * @param value        an Object
     * @param defaultValue Value to return if value is null, empty or convert failed.
     * @return Returns the converted value if it exists, or defaultValue
     */
    public static long toLong(Object value, final long defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException ex1) {
            LOGGER.error("toLong convert object to long failed, value: {}, throwable: ", value, ex1);
            try {
                return Double.valueOf(value.toString()).longValue();
            } catch (NumberFormatException ex2) {
                LOGGER.error("toLong convert object to double failed, value: {}, throwable: ", value, ex2);
                return defaultValue;
            }
        }
    }

    /**
     * Convert Object to Float
     * <pre>
     * TypeCast.toFloat(null, -1.0f) = -1.0
     * TypeCast.toFloat("", -1.0f)   = -1.0
     * </pre>
     *
     * @param value        an Object
     * @param defaultValue Value to return if value is null, empty or convert failed.
     * @return Returns the converted value if it exists, or defaultValue
     */
    public static float toFloat(Object value, final float defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            return Float.parseFloat(value.toString());
        } catch (NumberFormatException ex) {
            LOGGER.error("toFloat convert object to float failed, value: {}, throwable: ", value, ex);
            return defaultValue;
        }
    }

    /**
     * Convert Object to Double
     * <pre>
     * TypeCast.toDouble(null, -1.0) = -1.0
     * TypeCast.toDouble("", -1.0)   = -1.0
     * </pre>
     *
     * @param value        an Object
     * @param defaultValue Value to return if value is null, empty or convert failed.
     * @return Returns the converted value if it exists, or defaultValue
     */
    public static double toDouble(Object value, final double defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException ex) {
            LOGGER.error("toDouble convert object to double failed, value: {}, throwable: ", value, ex);
            return defaultValue;
        }
    }

    /**
     * Convert Bytes to Hex String
     *
     * @param value a Byte Array
     * @return Returns the converted value if it exists, or null
     */
    public static String toHex(byte[] value) {
        if (value != null) {
            BigInteger bInteger = new BigInteger(1, value);
            return bInteger.toString(16);
        } else {
            return null;
        }
    }

    /**
     * Convert Bytes to String, Use UTF-8
     *
     * @param value a Byte Array
     * @return Returns the converted value if it exists, or null
     */
    public static String toString(byte[] value) {
        return (value != null) ? new String(value, DEFAULT_CHARSET) : null;
    }

    /**
     * Convert String to Bytes, Use UTF-8
     *
     * @param value a String
     * @return Returns the converted value if it exists, or null
     */
    public static byte[] toBytes(String value) {
        return (value != null) ? value.getBytes(DEFAULT_CHARSET) : null;
    }

}
