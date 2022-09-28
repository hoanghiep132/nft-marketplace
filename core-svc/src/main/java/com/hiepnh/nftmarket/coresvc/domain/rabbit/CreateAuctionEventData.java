package com.hiepnh.nftmarket.coresvc.domain.rabbit;

import lombok.Data;

@Data
public class CreateAuctionEventData {

    private String itemUuid;

    private Long startTime;

    private Long endTime;

}
