package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

@Data
public class ListItemToMarketRequest extends BaseAuthRequest{

    private String uuid;

    private Double price;

    private String tnxHash;

    private String blockHash;

    private Long itemMarketId;
}
