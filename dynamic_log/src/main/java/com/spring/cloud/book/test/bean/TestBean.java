package com.spring.cloud.book.test.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TestBean
 * @author zhouyw
 * @date 2017.05.16
 */
public class TestBean implements Serializable {

    private static final long serialVersionUID = 1L;


    private String str;

    private Integer integer;

    private BigDecimal bigDecimal;

    private Date date;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
