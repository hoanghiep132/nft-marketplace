package com.hiepnh.nftmarket.apisvc.entites.dto;

import lombok.Data;

import javax.persistence.Column;

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
