package com.hiepnh.nftmarket.coresvc.service;

import com.hiepnh.nftmarket.coresvc.domain.rabbit.CreateAuctionEventData;

public interface AuctionService {

    void processCreateAuction(CreateAuctionEventData data);
}
