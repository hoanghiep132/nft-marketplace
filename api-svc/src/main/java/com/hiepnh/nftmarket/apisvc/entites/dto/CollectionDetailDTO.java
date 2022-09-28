package com.hiepnh.nftmarket.apisvc.entites.dto;


import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import lombok.Data;

import java.util.List;

@Data
public class CollectionDetailDTO {

    private String uuid;

    private String name;

    private String symbol;

    private String description;

    private String image;

    private String bannerImg;

    private List<String> categories;

    private String createBy;

    private String createAt;

    private List<ItemDTO> items;
}
