package com.hiepnh.nftmarket.coresvc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionData {

    private String itemUuid;

    private Integer type;
}
