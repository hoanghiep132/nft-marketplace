package com.hiepnh.nftmarket.apisvc.controller;

import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.common.ParseHeaderUtil;
import com.hiepnh.nftmarket.apisvc.domain.request.CancelAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateBidAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.dto.AuctionDetailDTO;
import com.hiepnh.nftmarket.apisvc.service.AuctionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auction")
public class AuctionController extends BaseController{

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/detail")
    public GetSingleItemResponse<AuctionDetailDTO> getAuctionDetail(@RequestHeader Map<String, String> headers,
                                                               @RequestParam(value = "itemUuid") String itemUuid) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>getAuctionDetail u: {}, itemUuid: {}", headerInfo, itemUuid);
        GetSingleItemResponse<AuctionDetailDTO> resp = auctionService.getAuctionDetail(itemUuid, headerInfo);
        logger.info("<=getAuctionDetail u: {}, itemUuid: {}, resp: {}", headerInfo, itemUuid, resp);
        return resp;
    }

    @PostMapping("/create")
    public BaseResponse createAuction(@RequestHeader Map<String, String> headers,
                                         @RequestBody CreateAuctionRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>createAuction u: {}, req: {}", headerInfo, request);
        request.setInfo(headerInfo);
        BaseResponse response = auctionService.createAuction(request);
        logger.info("<=createAuction u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }

    @PostMapping("/bid")
    public BaseResponse bidAuction(@RequestHeader Map<String, String> headers,
                                      @RequestBody CreateBidAuctionRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>bidAuction u: {}, req: {}", headerInfo, request);
        request.setInfo(headerInfo);
        BaseResponse response = auctionService.bidAuction(request);
        logger.info("<=bidAuction u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }

    @PostMapping("/cancel")
    public BaseResponse cancelAuction(@RequestHeader Map<String, String> headers,
                                      @RequestBody CancelAuctionRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>cancelAuction u: {}, req: {}", headerInfo, request);
        request.setInfo(headerInfo);
        BaseResponse response = auctionService.cancelAuction(request);
        logger.info("<=cancelAuction u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }
}
