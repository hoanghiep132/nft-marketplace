package com.hiepnh.nftmarket.apisvc.controller;

import com.hiepnh.nftmarket.apisvc.common.EnumCommon;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.common.ParseHeaderUtil;
import com.hiepnh.nftmarket.apisvc.domain.request.*;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetArrayResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import com.hiepnh.nftmarket.apisvc.entites.ListingMarketEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.ItemDetailDTO;
import com.hiepnh.nftmarket.apisvc.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/item")
public class MyItemController extends BaseController {

    private final ItemService itemService;

    public MyItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public GetArrayResponse<ItemEntity> searchItem(@RequestHeader Map<String, String> headers,
                                                   @RequestParam(value = "collectionUuid", required = false) String collectionUuid,
                                                   @RequestParam(value = "text", required = false, defaultValue = "") String text,
                                                   @RequestParam(value = "orderSort", required = false, defaultValue = "RECENTLY_CREATED") EnumCommon.OrderSort orderSort,
                                                   @RequestParam(value = "status", required = false, defaultValue = "0") Integer status,
                                                   @RequestParam(value = "min", required = false, defaultValue = "0") Double min,
                                                   @RequestParam(value = "max", required = false, defaultValue = "100000000") Double max,
                                                   @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "100") Integer size) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>searchItem text: {}", text);
        GetArrayResponse<ItemEntity> resp = itemService.searchItem(text,  collectionUuid, orderSort, status, min, max, page, size, headerInfo);
        logger.info("<=searchItem resp: {}", resp);
        return resp;
    }

    @GetMapping("/favorites")
    public GetArrayResponse<ItemEntity> getItemFavoriteList(@RequestHeader Map<String, String> headers,
                                                   @RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "size", required = false) Integer size) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        GetArrayResponse<ItemEntity> resp = itemService.getItemFavoriteList(page, size, headerInfo);
        logger.info("<=getItemFavoriteList  resp: {}", resp);
        return resp;
    }

    @GetMapping("/owner")
    public GetArrayResponse<ItemEntity> getOwnerItemList(@RequestHeader Map<String, String> headers,
                                                            @RequestParam(value = "page", required = false) Integer page,
                                                            @RequestParam(value = "size", required = false) Integer size) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        GetArrayResponse<ItemEntity> resp = itemService.getOwnerItemList(page, size, headerInfo);
        logger.info("<=getItemFavoriteList  resp: {}", resp);
        return resp;
    }


    @GetMapping("/detail/{uuid}")
    public GetSingleItemResponse<ItemDetailDTO> getItemDetail(@RequestHeader Map<String, String> headers,
                                                              @PathVariable("uuid") String uuid) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>getItemDetail u: {}, uuid: {}", headerInfo, uuid);
        GetSingleItemResponse<ItemDetailDTO> resp = itemService.findItemByUuid(uuid, headerInfo);
        logger.info("=>getItemDetail u: {}, uuid: {}, resp: {}", headerInfo, uuid, resp);
        return resp;
    }

    @PostMapping("/create")
    public GetSingleItemResponse<String> createItem(@RequestHeader Map<String, String> headers,
                                   @RequestBody CreateItemRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        request.setInfo(headerInfo);
        logger.info("=>createItem u: {}, req: {}", headerInfo, request);
        GetSingleItemResponse<String> response = itemService.createItem(request);
        logger.info("<=createItem u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }


    @PostMapping("/update")
    public BaseResponse updateItem(@RequestHeader Map<String, String> headers,
                                   @RequestBody UpdateItemRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>updateItem u: {}, req: {}", headerInfo, request);
        BaseResponse response = itemService.updateItem(request);
        logger.info("<=updateItem u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }


    @PostMapping("/delete")
    public BaseResponse deleteItem(@RequestHeader Map<String, String> headers,
                                   @RequestBody DeleteDataRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>deleteItem u: {}, req: {}", headerInfo, request);
        BaseResponse response = itemService.deleteItem(request);
        logger.info("<=deleteItem u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }

    @PostMapping("/listing")
    public BaseResponse listingItem(@RequestHeader Map<String, String> headers,
                                   @RequestBody ListItemToMarketRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        request.setInfo(headerInfo);
        logger.info("=>listingItem u: {}, req: {}", headerInfo, request);
        BaseResponse response = itemService.listItemToMarket(request);
        logger.info("<=listingItem u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }

    @PostMapping("/buy")
    public BaseResponse listingItem(@RequestHeader Map<String, String> headers,
                                    @RequestBody BuyItemRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        request.setInfo(headerInfo);
        logger.info("=>listingItem u: {}, req: {}", headerInfo, request);
        BaseResponse response = itemService.buyItem(request);
        logger.info("<=listingItem u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }

    @PostMapping("/cancel-listing")
    public BaseResponse cancelListingItem(@RequestHeader Map<String, String> headers,
                                    @RequestBody CancelListingItemRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        request.setInfo(headerInfo);
        logger.info("=>cancelListingItem u: {}, req: {}", headerInfo, request);
        BaseResponse response = itemService.cancelListItemToMarket(request);
        logger.info("<=cancelListingItem u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }

    @GetMapping("/like/{itemUuid}")
    public BaseResponse likeItem(
            @PathVariable("itemUuid") String itemUuid,
            @RequestHeader Map<String, String> headers){
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        BaseResponse response = itemService.likeItem(itemUuid, headerInfo);
        logger.info("<=likeItem u: {}, resp: {}", headerInfo, response);
        return response;
    }

    @GetMapping("/unlike/{itemUuid}")
    public BaseResponse unlikeItem(
            @PathVariable("itemUuid") String itemUuid,
            @RequestHeader Map<String, String> headers){
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        BaseResponse response = itemService.unlikeItem(itemUuid, headerInfo);
        logger.info("<=likeItem u: {}, resp: {}", headerInfo, response);
        return response;
    }
}
