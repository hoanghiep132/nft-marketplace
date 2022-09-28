package com.hiepnh.nftmarket.accountsvc.domain.dto;

import lombok.Data;

@Data
public class CollectionDTO {

    private String uuid;

    private String imageUrl;

    private String bannerImg;

    private String name;

    private String symbol;

    private String description;

    private Integer total;
}
