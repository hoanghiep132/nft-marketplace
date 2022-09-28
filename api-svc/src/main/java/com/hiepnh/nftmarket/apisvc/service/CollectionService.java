package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.common.EnumCommon;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
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

public interface CollectionService {
    GetArrayResponse<CollectionEntity> searchCollection(String text, String category, int page, int size, HeaderInfo headerInfo);

    GetArrayResponse<CollectionEntity> searchCollection(String text, String category, int page, int size);

    GetSingleItemResponse<CollectionDetailDTO> getCollectionDetail(String uuid, int page, int size, HeaderInfo headerInfo);

    GetSingleItemResponse<CollectionDetailDTO> getCollectionDetail(String uuid, int page, int size);

    GetArrayResponse<CollectionDTO> searchCollectionByUser(int page, int size, HeaderInfo headerInfo);

    GetArrayResponse<CollectionDTO> searchCollectionByWalletAddress(int page, int size, String walletAddess);

    GetArrayResponse<CollectionContractDTO> getOwnCollectionList(HeaderInfo headerInfo);

    GetSingleItemResponse<String> createCollection(CreateCollectionRequest request);

    BaseResponse updateCollection(UpdateCollectionRequest request);

    BaseResponse deleteItem(DeleteDataRequest request);
}
