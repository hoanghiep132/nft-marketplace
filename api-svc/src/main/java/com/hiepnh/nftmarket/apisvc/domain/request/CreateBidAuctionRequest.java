package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

@Data
public class CreateBidAuctionRequest extends BaseAuthRequest{

    private String itemUuid;

    private Double price;

    private String tnxHash;

    private String blockHash;
}
