package com.hiepnh.nftmarket.apisvc.entites.dto;

import com.hiepnh.nftmarket.apisvc.entites.CollectionEntity;
import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import com.hiepnh.nftmarket.apisvc.entites.model.ItemBidHistoryModel;
import com.hiepnh.nftmarket.apisvc.entites.model.ItemBuyingHistoryModel;
import com.hiepnh.nftmarket.apisvc.entites.model.NftItemContractModel;
import lombok.Data;

import java.util.List;

@Data
public class ItemDetailDTO {

    private String uuid;

    private String name;

    private String description;

    private String image;

    private Double price;

    private Integer status;

    private Boolean isFavorite;

    private CollectionEntity collection;

    private String createBy;

    private String createAt;

    private String owner;

    private NftItemContractModel contractDetail;

    private List<ItemBuyingHistoryModel> history;

    private List<ItemEntity> others;

    private Long itemMarketId;

    private Long itemActionId;

    private String endTime;

    private Long endAt;

    private Long remainTime;

    private List<ItemBidHistoryModel> bidHistory;
}
