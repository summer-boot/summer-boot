package io.github.summer.boot.value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 值
 *
 * @author changebooks@qq.com
 */
public final class Value implements Serializable {
    /**
     * 类型
     */
    private int type;

    /**
     * 字符串
     */
    private String valueString;

    /**
     * 整数
     */
    private Integer valueInteger;

    /**
     * 长整数
     */
    private Long valueLong;

    /**
     * 小数
     */
    private BigDecimal valueBigDecimal;

    /**
     * 日期时间
     */
    private Date valueDate;

    public Value(String valueString) {
        setType(Types.STRING);
        setValueString(valueString);
    }

    public Value(Integer valueInteger) {
        setType(Types.INTEGER);
        setValueInteger(valueInteger);
    }

    public Value(Long valueLong) {
        setType(Types.LONG);
        setValueLong(valueLong);
    }

    public Value(BigDecimal valueBigDecimal) {
        setType(Types.BIG_DECIMAL);
        setValueBigDecimal(valueBigDecimal);
    }

    public Value(Date valueDate) {
        setType(Types.DATE);
        setValueDate(valueDate);
    }

    /**
     * 获取值，忽略类型
     *
     * @return an object representation of the object
     */
    public Object toObject() {
        int type = getType();
        return switch (type) {
            case Types.STRING -> getValueString();
            case Types.INTEGER -> getValueInteger();
            case Types.LONG -> getValueLong();
            case Types.BIG_DECIMAL -> getValueBigDecimal();
            case Types.DATE -> getValueDate();
            default -> throw new UnsupportedValueTypeException(type);
        };
    }

    @Override
    public String toString() {
        int type = getType();
        return switch (type) {
            case Types.STRING -> getValueString();
            case Types.INTEGER -> toString(getValueInteger());
            case Types.LONG -> toString(getValueLong());
            case Types.BIG_DECIMAL -> toString(getValueBigDecimal());
            case Types.DATE -> toString(getValueDate());
            default -> throw new UnsupportedValueTypeException(type);
        };
    }

    /**
     * 整数 to 字符串
     *
     * @param valueInteger 整数
     * @return 字符串
     */
    private String toString(Integer valueInteger) {
        return valueInteger != null ? valueInteger.toString() : null;
    }

    /**
     * 长整数 to 字符串
     *
     * @param valueLong 长整数
     * @return 字符串
     */
    private String toString(Long valueLong) {
        return valueLong != null ? valueLong.toString() : null;
    }

    /**
     * 小数 to 字符串
     *
     * @param valueBigDecimal 小数
     * @return 字符串
     */
    private String toString(BigDecimal valueBigDecimal) {
        return valueBigDecimal != null ? valueBigDecimal.toString() : null;
    }

    /**
     * 日期时间 to 字符串
     *
     * @param valueDate 日期时间
     * @return 字符串
     */
    private String toString(Date valueDate) {
        return valueDate != null ? valueDate.toString() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Value that = (Value) o;

        int type = getType();
        if (type != that.getType()) {
            return false;
        }

        return switch (type) {
            case Types.STRING -> Objects.equals(getValueString(), that.getValueString());
            case Types.INTEGER -> Objects.equals(getValueInteger(), that.getValueInteger());
            case Types.LONG -> Objects.equals(getValueLong(), that.getValueLong());
            case Types.BIG_DECIMAL -> Objects.equals(getValueBigDecimal(), that.getValueBigDecimal());
            case Types.DATE -> Objects.equals(getValueDate(), that.getValueDate());
            default -> throw new UnsupportedValueTypeException(type);
        };
    }

    @Override
    public int hashCode() {
        int type = getType();
        return switch (type) {
            case Types.STRING -> Objects.hash(getValueString());
            case Types.INTEGER -> Objects.hash(getValueInteger());
            case Types.LONG -> Objects.hash(getValueLong());
            case Types.BIG_DECIMAL -> Objects.hash(getValueBigDecimal());
            case Types.DATE -> Objects.hash(getValueDate());
            default -> throw new UnsupportedValueTypeException(type);
        };
    }

    public int getType() {
        return type;
    }

    private void setType(int type) {
        this.type = type;
    }

    public String getValueString() {
        return valueString;
    }

    private void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Integer getValueInteger() {
        return valueInteger;
    }

    private void setValueInteger(Integer valueInteger) {
        this.valueInteger = valueInteger;
    }

    public Long getValueLong() {
        return valueLong;
    }

    private void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    public BigDecimal getValueBigDecimal() {
        return valueBigDecimal;
    }

    private void setValueBigDecimal(BigDecimal valueBigDecimal) {
        this.valueBigDecimal = valueBigDecimal;
    }

    public Date getValueDate() {
        return valueDate;
    }

    private void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

}
