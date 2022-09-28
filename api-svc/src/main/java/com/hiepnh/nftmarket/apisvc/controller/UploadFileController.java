package com.hiepnh.nftmarket.apisvc.controller;


import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.common.ParseHeaderUtil;
import com.hiepnh.nftmarket.apisvc.domain.request.DeleteFileUploadRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.service.UploadFileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/file")
public class UploadFileController extends BaseController {

    private final UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/upload")
    public GetSingleItemResponse<String> uploadFile(@RequestHeader Map<String, String> headers,
                                                    @RequestParam(value = "file") MultipartFile file) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        logger.info("=>UploadFile u: {} file: {}", headerInfo.getUsername(), file);
        GetSingleItemResponse<String> response = uploadFileService.uploadFile(file, headerInfo
        );
        logger.info("=>UploadFile u: {} file: {}, resp: {}", headerInfo.getUsername(), file, response);
        return response;
    }

    @PostMapping("/detele")
    public BaseResponse deleteFile(@RequestHeader Map<String, String> headers,
                                   @RequestBody DeleteFileUploadRequest request) {
        HeaderInfo headerInfo = ParseHeaderUtil.build(headers);
        request.setInfo(headerInfo);
        logger.info("=>deleteFile u: {} req: {}", headerInfo.getUsername(), request);
        BaseResponse response = uploadFileService.deleteFile(request);
        logger.info("=>deleteFile u: {} req: {}, resp: {}", headerInfo.getUsername(), request, response);
        return response;
    }
}
