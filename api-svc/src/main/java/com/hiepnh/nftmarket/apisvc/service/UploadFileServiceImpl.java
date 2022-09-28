package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.domain.request.DeleteFileUploadRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl extends BaseService implements UploadFileService {
    @Override
    public GetSingleItemResponse<String> uploadFile(MultipartFile multipartFile, HeaderInfo headerInfo) {
        return null;
    }

    @Override
    public BaseResponse deleteFile(DeleteFileUploadRequest request) {
        return null;
    }
}
