package io.github.summer.boot.value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 值数组
 *
 * @author changebooks@qq.com
 */
public final class Values implements Serializable {
    /**
     * 类型
     */
    private int type;

    /**
     * 字符串
     */
    private String[] valueString;

    /**
     * 整数
     */
    private Integer[] valueInteger;

    /**
     * 长整数
     */
    private Long[] valueLong;

    /**
     * 小数
     */
    private BigDecimal[] valueBigDecimal;

    /**
     * 日期时间
     */
    private Date[] valueDate;

    public Values(String[] valueString) {
        setType(Types.STRING);
        setValueString(valueString);
    }

    public Values(Integer[] valueInteger) {
        setType(Types.INTEGER);
        setValueInteger(valueInteger);
    }

    public Values(Long[] valueLong) {
        setType(Types.LONG);
        setValueLong(valueLong);
    }

    public Values(BigDecimal[] valueBigDecimal) {
        setType(Types.BIG_DECIMAL);
        setValueBigDecimal(valueBigDecimal);
    }

    public Values(Date[] valueDate) {
        setType(Types.DATE);
        setValueDate(valueDate);
    }

    public int getType() {
        return type;
    }

    private void setType(int type) {
        this.type = type;
    }

    public String[] getValueString() {
        return valueString;
    }

    private void setValueString(String[] valueString) {
        this.valueString = valueString;
    }

    public Integer[] getValueInteger() {
        return valueInteger;
    }

    private void setValueInteger(Integer[] valueInteger) {
        this.valueInteger = valueInteger;
    }

    public Long[] getValueLong() {
        return valueLong;
    }

    private void setValueLong(Long[] valueLong) {
        this.valueLong = valueLong;
    }

    public BigDecimal[] getValueBigDecimal() {
        return valueBigDecimal;
    }

    private void setValueBigDecimal(BigDecimal[] valueBigDecimal) {
        this.valueBigDecimal = valueBigDecimal;
    }

    public Date[] getValueDate() {
        return valueDate;
    }

    private void setValueDate(Date[] valueDate) {
        this.valueDate = valueDate;
    }

}
