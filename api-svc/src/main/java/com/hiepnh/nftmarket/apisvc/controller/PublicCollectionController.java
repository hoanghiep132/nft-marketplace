package com.hiepnh.nftmarket.apisvc.controller;

import com.hiepnh.nftmarket.apisvc.domain.response.GetArrayResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.CollectionEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.CollectionDetailDTO;
import com.hiepnh.nftmarket.apisvc.service.CollectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/collection")
public class PublicCollectionController extends BaseController{

    private final CollectionService collectionService;

    public PublicCollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/search")
    public GetArrayResponse<CollectionEntity> searchCollection(@RequestParam(value = "text", required = false, defaultValue = "") String text,
                                                               @RequestParam(value = "category", required = false, defaultValue = "") String category,
                                                               @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        logger.info("=>searchCollection text: {}, category: {}, page: {}, size: {}", text, category, page, size);
        GetArrayResponse<CollectionEntity> resp = collectionService.searchCollection(text, category, page, size);
        logger.info("<=searchCollection text: {}, category: {}, page: {}, size: {}, resp: {}", text, category, page, size, resp.info());
        return resp;
    }


    @GetMapping("/detail/{uuid}")
    public GetSingleItemResponse<CollectionDetailDTO> getCollectionDetail(@RequestParam(value = "page", required = false) Integer page,
                                                                          @RequestParam(value = "size", required = false) Integer size,
                                                                          @PathVariable("uuid") String uuid) {
        logger.info("=>getCollectionDetail uuid: {}", uuid);
        GetSingleItemResponse<CollectionDetailDTO> resp = collectionService.getCollectionDetail(uuid, page, size);
        logger.info("=>getCollectionDetail uuid: {}, resp: {}", uuid, resp);
        return resp;
    }
}
