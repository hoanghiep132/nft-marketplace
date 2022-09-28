package com.hiepnh.nftmarket.apisvc.entites.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemBidHistoryModel {

    private String address;

    private Double price;

    private String time;

}
