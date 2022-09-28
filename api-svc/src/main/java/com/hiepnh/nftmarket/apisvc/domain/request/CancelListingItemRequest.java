package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

@Data
public class CancelListingItemRequest extends BaseAuthRequest{

    private String uuid;

    private String tnxHash;

    private String blockHash;
}
