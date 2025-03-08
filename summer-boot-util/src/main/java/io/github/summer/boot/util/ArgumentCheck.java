package io.github.summer.boot.util;

import io.github.summer.boot.base.IResult;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * 前提条件，标准提示
 *
 * @author changebooks@qq.com
 */
public final class ArgumentCheck {

    private ArgumentCheck() {
    }

    /**
     * 正确？is true
     * %s must be true
     *
     * @param b      the boolean
     * @param result the data transfer interface
     * @throws ArgumentException if the value is false
     */
    public static void isTrue(boolean b, IResult result) {
        if (!b) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 错误？is false
     * %s must be false
     *
     * @param b      the boolean
     * @param result the data transfer interface
     * @throws ArgumentException if the value is true
     */
    public static void isFalse(boolean b, IResult result) {
        if (b) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 空对象？is null
     * %s must be null
     *
     * @param obj    the object
     * @param result the data transfer interface
     * @throws ArgumentException if the object is not null
     */
    public static void isNull(Object obj, IResult result) {
        if (obj != null) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非空对象？not null
     * %s must not be null
     *
     * @param obj    the object
     * @param result the data transfer interface
     * @throws ArgumentException if the object is null
     */
    public static void nonNull(Object obj, IResult result) {
        if (obj == null) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非空字符串？not null and not empty ("")
     * %s must not be null
     * %s must not be empty
     *
     * @param str    the string
     * @param result the data transfer interface
     * @throws ArgumentException if the str is null
     * @throws ArgumentException if the str is empty
     */
    public static void nonEmpty(String str, IResult result) {
        nonNull(str, result);

        if (str.isEmpty()) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非空集合？not null and not empty
     * %s must not be null
     * %s must not be empty
     *
     * @param collection the collection
     * @param result     the data transfer interface
     * @throws ArgumentException if the collection is null
     * @throws ArgumentException if the collection is empty
     */
    public static void nonEmpty(Collection<?> collection, IResult result) {
        nonNull(collection, result);

        if (collection.isEmpty()) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非空Map？not null and not empty
     * %s must not be null
     * %s must not be empty
     *
     * @param map    the map
     * @param result the data transfer interface
     * @throws ArgumentException if the map is null
     * @throws ArgumentException if the map is empty
     */
    public static void nonEmpty(Map<?, ?> map, IResult result) {
        nonNull(map, result);

        if (map.isEmpty()) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非零？not null and unequal than 0
     * %s must not be null
     * %s must not be equal than 0
     *
     * @param num    the int
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is equal than 0
     */
    public static void nonZero(Integer num, IResult result) {
        nonNull(num, result);

        if (num == 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非零？not null and unequal than 0
     * %s must not be null
     * %s must not be equal than 0
     *
     * @param num    the long
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is equal than 0
     */
    public static void nonZero(Long num, IResult result) {
        nonNull(num, result);

        if (num == 0L) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非零？not null and unequal than 0
     * %s must not be null
     * %s must not be equal than 0
     *
     * @param num    the decimal
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is equal than 0
     */
    public static void nonZero(BigDecimal num, IResult result) {
        nonNull(num, result);

        if (num.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 正数？not null and greater than 0
     * %s must not be null
     * %s must be greater than 0
     *
     * @param num    the int
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is less or equal than 0
     */
    public static void isPositive(Integer num, IResult result) {
        nonNull(num, result);

        if (num <= 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 正数？not null and greater than 0
     * %s must not be null
     * %s must be greater than 0
     *
     * @param num    the long
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is less or equal than 0
     */
    public static void isPositive(Long num, IResult result) {
        nonNull(num, result);

        if (num <= 0L) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 正数？not null and greater than 0
     * %s must not be null
     * %s must be greater than 0
     *
     * @param num    the decimal
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is less or equal than 0
     */
    public static void isPositive(BigDecimal num, IResult result) {
        nonNull(num, result);

        if (num.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 正数？null or greater than 0
     * %s must be greater than 0
     *
     * @param num    the int
     * @param result the data transfer interface
     * @throws ArgumentException if the num is less or equal than 0
     */
    public static void isNullOrPositive(Integer num, IResult result) {
        if (num != null && num <= 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 正数？null or greater than 0
     * %s must be greater than 0
     *
     * @param num    the long
     * @param result the data transfer interface
     * @throws ArgumentException if the num is less or equal than 0
     */
    public static void isNullOrPositive(Long num, IResult result) {
        if (num != null && num <= 0L) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 正数？null or greater than 0
     * %s must be greater than 0
     *
     * @param num    the decimal
     * @param result the data transfer interface
     * @throws ArgumentException if the num is less or equal than 0
     */
    public static void isNullOrPositive(BigDecimal num, IResult result) {
        if (num != null && num.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非负数？not null and (greater or equal than 0)
     * %s must not be null
     * %s must not be less than 0
     *
     * @param num    the int
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is less than 0
     */
    public static void nonNegative(Integer num, IResult result) {
        nonNull(num, result);

        if (num < 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非负数？not null and (greater or equal than 0)
     * %s must not be null
     * %s must not be less than 0
     *
     * @param num    the long
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is less than 0
     */
    public static void nonNegative(Long num, IResult result) {
        nonNull(num, result);

        if (num < 0L) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非负数？not null and (greater or equal than 0)
     * %s must not be null
     * %s must not be less than 0
     *
     * @param num    the decimal
     * @param result the data transfer interface
     * @throws ArgumentException if the num is null
     * @throws ArgumentException if the num is less than 0
     */
    public static void nonNegative(BigDecimal num, IResult result) {
        nonNull(num, result);

        if (num.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非负数？null or (greater or equal than 0)
     * %s must not be less than 0
     *
     * @param num    the int
     * @param result the data transfer interface
     * @throws ArgumentException if the num is less than 0
     */
    public static void nullOrNonNegative(Integer num, IResult result) {
        if (num != null && num < 0) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非负数？null or (greater or equal than 0)
     * %s must not be less than 0
     *
     * @param num    the long
     * @param result the data transfer interface
     * @throws ArgumentException if the num is less than 0
     */
    public static void nullOrNonNegative(Long num, IResult result) {
        if (num != null && num < 0L) {
            throw new ArgumentException(result);
        }
    }

    /**
     * 非负数？null or (greater or equal than 0)
     * %s must not be less than 0
     *
     * @param num    the decimal
     * @param result the data transfer interface
     * @throws ArgumentException if the num is less than 0
     */
    public static void nullOrNonNegative(BigDecimal num, IResult result) {
        if (num != null && num.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArgumentException(result);
        }
    }

}
