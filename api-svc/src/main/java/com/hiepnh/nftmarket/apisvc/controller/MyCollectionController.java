package com.hiepnh.nftmarket.apisvc.controller;

import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.common.ParseHeaderUtil;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateCollectionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.DeleteDataRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.UpdateCollectionRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetArrayResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.CollectionEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.CollectionContractDTO;
import com.hiepnh.nftmarket.apisvc.entites.dto.CollectionDTO;
import com.hiepnh.nftmarket.apisvc.entites.dto.CollectionDetailDTO;
import com.hiepnh.nftmarket.apisvc.service.CollectionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/collection")
public class MyCollectionController extends BaseController {

    private final CollectionService collectionService;

    public MyCollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/search")
    public GetArrayResponse<CollectionEntity> searchCollection(@RequestHeader Map<String, String> headers,
                                                               @RequestParam(value = "text", required = false) String text,
                                                               @RequestParam(value = "category", required = false) String category,
                                                               @RequestParam(value = "page", required = false) Integer page,
                                                               @RequestParam(value = "size", required = false) Integer size) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>searchCollection u: {}, text: {}, category: {}, page: {}, size: {}", headerInfo, text, category, page, size);
        GetArrayResponse<CollectionEntity> resp = collectionService.searchCollection(text, category, page, size, headerInfo);
        logger.info("<=searchCollection u: {}, text: {}, category: {}, page: {}, size: {}, resp: {}", headerInfo, text, category, page, size, resp.info());
        return resp;
    }


    @GetMapping("/detail/{uuid}")
    public GetSingleItemResponse<CollectionDetailDTO> getCollectionDetail(@RequestHeader Map<String, String> headers,
                                                                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                                          @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                                          @PathVariable("uuid") String uuid) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>getCollectionDetail u: {}, uuid: {}", headerInfo, uuid);
        GetSingleItemResponse<CollectionDetailDTO> resp = collectionService.getCollectionDetail(uuid, page, size, headerInfo);
        logger.info("=>getCollectionDetail u: {}, uuid: {}, resp: {}", headerInfo, uuid, resp);
        return resp;
    }

    @GetMapping("/owner")
    public GetArrayResponse<CollectionDTO> getMyCollection(@RequestHeader Map<String, String> headers,
                                                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "100") Integer size) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>getMyCollection u: {}", headerInfo);
        GetArrayResponse<CollectionDTO> resp = collectionService.searchCollectionByUser(page, size, headerInfo);
        logger.info("=>getMyCollection u: {}, resp: {}", headerInfo, resp);
        return resp;
    }

    @GetMapping("/owner/list")
    public GetArrayResponse<CollectionContractDTO> getMyCollectionList(@RequestHeader Map<String, String> headers) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>getMyCollectionList u: {}", headerInfo);
        GetArrayResponse<CollectionContractDTO> resp = collectionService.getOwnCollectionList(headerInfo);
        logger.info("=>getMyCollectionList u: {}, resp: {}", headerInfo, resp);
        return resp;
    }

    @PostMapping("/create")
    public GetSingleItemResponse<String> createCollection(@RequestHeader Map<String, String> headers,
                                                          @RequestBody CreateCollectionRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>createCollection u: {}, req: {}", headerInfo, request);
        request.setInfo(headerInfo);
        GetSingleItemResponse<String> response = collectionService.createCollection(request);
        logger.info("<=createCollection u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }


    @PostMapping("/update")
    public BaseResponse updateCollection(@RequestHeader Map<String, String> headers,
                                         @RequestBody UpdateCollectionRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>updateCollection u: {}, req: {}", headerInfo, request);
        BaseResponse response = request.validate();
        if (response == null) {
            request.setInfo(headerInfo);
            response = collectionService.updateCollection(request);
        }
        logger.info("<=updateCollection u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }


    @PostMapping("/delete")
    public BaseResponse deleteCollection(@RequestHeader Map<String, String> headers,
                                         @RequestBody DeleteDataRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>deleteCollection u: {}, req: {}", headerInfo, request);
        BaseResponse response = collectionService.deleteItem(request);
        logger.info("<=deleteCollection u: {}, req: {}, resp: {}", headerInfo, request, response);
        return response;
    }

}
