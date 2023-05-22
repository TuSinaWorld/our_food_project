package com.s3.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Order implements Serializable {

    private String ono;
    private String odate;
    private String ano;
    private String sdate;
    private String rdate;
    private int status;
    private Double price;
    private int invoice;

    private String mno;


    private List<OrderItem> orderItem;


}
