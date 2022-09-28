package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

@Data
public class CancelAuctionRequest extends BaseAuthRequest{

    private String itemUuid;

    private String tnxHash;

    private String blockHash;
}
