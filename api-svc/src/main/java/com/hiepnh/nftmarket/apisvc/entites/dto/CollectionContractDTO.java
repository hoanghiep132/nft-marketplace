package com.hiepnh.nftmarket.apisvc.entites.dto;

import lombok.Data;

@Data
public class CollectionContractDTO {

    private String collectionUuid;

    private String name;

    private String symbol;

    private String contractAddress;
}
