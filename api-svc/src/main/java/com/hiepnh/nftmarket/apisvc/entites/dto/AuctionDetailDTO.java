package com.hiepnh.nftmarket.apisvc.entites.dto;

import com.hiepnh.nftmarket.apisvc.entites.AuctionEntity;
import com.hiepnh.nftmarket.apisvc.entites.BidTransactionEntity;
import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import lombok.Data;

import java.util.List;

@Data
public class AuctionDetailDTO {

    private String uuid;

    private ItemEntity itemEntity;

    private AuctionEntity auction;

    private List<BidTransactionEntity> bidList;
}
