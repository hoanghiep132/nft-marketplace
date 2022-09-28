package com.hiepnh.nftmarket.apisvc.controller;

import com.hiepnh.nftmarket.apisvc.common.EnumCommon;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.common.ParseHeaderUtil;
import com.hiepnh.nftmarket.apisvc.domain.response.GetArrayResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.ItemDetailDTO;
import com.hiepnh.nftmarket.apisvc.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public/item")
public class PublicItemController extends BaseController{

    private final ItemService itemService;

    public PublicItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public GetArrayResponse<ItemEntity> searchItem(@RequestParam(value = "collectionUuid", required = false) String collectionUuid,
                                                   @RequestParam(value = "text", required = false, defaultValue = "") String text,
                                                   @RequestParam(value = "orderSort", required = false, defaultValue = "RECENTLY_CREATED") EnumCommon.OrderSort orderSort,
                                                   @RequestParam(value = "status", required = false, defaultValue = "0") Integer status,
                                                   @RequestParam(value = "min", required = false, defaultValue = "0") Double min,
                                                   @RequestParam(value = "max", required = false, defaultValue = "100000000") Double max,
                                                   @RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "size", required = false) Integer size) {
        logger.info("=>searchItem text: {}", text);
        GetArrayResponse<ItemEntity> resp = itemService.searchItem(text,  collectionUuid, orderSort, status, min, max, page, size);
        logger.info("<=searchItem  resp: {}", resp);
        return resp;
    }


    @GetMapping("/{uuid}")
    public GetSingleItemResponse<ItemDetailDTO> getItemDetail(@PathVariable("uuid") String uuid) {
        logger.info("=>getItemDetail uuid: {}", uuid);
        GetSingleItemResponse<ItemDetailDTO> resp = itemService.findItemByUuid(uuid);
        logger.info("=>getItemDetail uuid: {}, resp: {}", uuid, resp);
        return resp;
    }
}
