package com.hiepnh.nftmarket.apisvc.entites.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ItemDTO {

    private String uuid;

    private String name;

    private String description;

    private String imageUrl;

    private Integer status;

    private Double price;

    private Boolean isFavorite;
}
