package com.hiepnh.nftmarket.apisvc.service;


import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.domain.request.DeleteFileUploadRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

    GetSingleItemResponse<String> uploadFile(final MultipartFile multipartFile, HeaderInfo headerInfo);

    BaseResponse deleteFile(DeleteFileUploadRequest request);

}
