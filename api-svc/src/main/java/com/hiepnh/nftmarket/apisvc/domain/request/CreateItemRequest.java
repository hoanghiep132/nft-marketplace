package com.hiepnh.nftmarket.apisvc.domain.request;

import lombok.Data;

@Data
public class CreateItemRequest extends BaseAuthRequest{

    private String collectionUuid;

    private String name;

    private String description;

    private String imageUrl;

    private String tnxHash;

    private String blockHash;

    private Long tokenId;
}
