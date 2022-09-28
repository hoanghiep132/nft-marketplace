package com.hiepnh.nftmarket.apisvc.domain.rabbitmq;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAuctionEventData {

    private String itemUuid;

    private Long startTime;

    private Long endTime;

}
