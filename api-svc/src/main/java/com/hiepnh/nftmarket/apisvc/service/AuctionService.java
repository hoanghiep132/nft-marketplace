package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.domain.request.CancelAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateBidAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.dto.AuctionDetailDTO;

public interface AuctionService {

    GetSingleItemResponse<AuctionDetailDTO> getAuctionDetail(String itemUuid, HeaderInfo headerInfo);

    BaseResponse createAuction(CreateAuctionRequest request);

    BaseResponse bidAuction(CreateBidAuctionRequest request);

    BaseResponse cancelAuction(CancelAuctionRequest request);
}
