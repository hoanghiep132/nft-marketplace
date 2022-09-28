package com.hiepnh.nftmarket.coresvc.domain.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
public class AuctionEventEntity extends EventEntity{

    private Object auction;

    public AuctionEventEntity(String type, Object auction) {
        super(type);
        this.auction = auction;
    }
}
