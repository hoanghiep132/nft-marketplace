package com.hiepnh.nftmarket.apisvc.entites.model;

import lombok.Data;

@Data
public class ItemBuyingHistoryModel {

    private Integer id;

    private String address;

    private Double price;

    private String time;
}
