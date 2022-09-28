package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

@Data
public class CreateAuctionRequest extends BaseAuthRequest{

    private String itemUuid;

    private String txnHash;

    private String blockHash;

//    private String startTime;
//
//    private String endTime;

    private Long startTime;

    private Long endTime;

    private Double startPrice;

    private Long auctionMapId;
}
