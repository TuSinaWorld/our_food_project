package com.s3.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItem implements Serializable {
    private String gno;
    private String mno;
    private int nums;


    private String gname;
    private int tno;
    private Double price;
    private int balance;
    private String pics;
    private String unit;
    private String weight;

    private int count;
    private Double smallCount;



    @Override
    public String toString() {
        return "OrderItem{" +
                "gno='" + gno + '\'' +
                ", mno='" + mno + '\'' +
                ", nums=" + nums +
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
