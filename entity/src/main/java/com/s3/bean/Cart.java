package com.s3.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Cart implements Serializable {
    private String cno;
    private String gno;
    private String mno;
    private int num;


    private String gname;
    private int tno;
    private Double price;
    private int balance;
    private String pics;
    private String unit;
    private String weight;

    private int count;
    private Double smallCount;
    private boolean status;


    @Override
    public String toString() {
        return "Cart{" +
                "cno='" + cno + '\'' +
                ", gno='" + gno + '\'' +
                ", mno='" + mno + '\'' +
                ", num=" + num +
                ", gname='" + gname + '\'' +
                ", tno=" + tno +
                ", price=" + price +
                ", balance=" + balance +
                ", pics='" + pics + '\'' +
                ", unit='" + unit + '\'' +
                ", weight='" + weight + '\'' +
                ", count=" + count +
                ", smallCount=" + smallCount +
                '}';
    }
}
