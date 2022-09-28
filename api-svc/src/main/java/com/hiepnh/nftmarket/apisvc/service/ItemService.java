package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.common.EnumCommon.*;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.domain.request.*;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetArrayResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.ItemDetailDTO;

public interface ItemService {
    GetArrayResponse<ItemEntity> searchItem(String text, String collectionUuid, OrderSort orderSort, Integer status, Double min, Double max, int page, int size, HeaderInfo headerInfo);

    GetArrayResponse<ItemEntity> searchItem(String text, String collectionUuid, OrderSort orderSort, Integer status, Double min, Double max, int page, int size);

    GetArrayResponse<ItemEntity> getItemFavoriteList(int page, int size, HeaderInfo headerInfo);

    GetArrayResponse<ItemEntity> getOwnerItemList(int page, int size, HeaderInfo headerInfo);
    GetSingleItemResponse<ItemDetailDTO> findItemByUuid(String uuid, HeaderInfo headerInfo);

    GetSingleItemResponse<ItemDetailDTO> findItemByUuid(String uuid);

    GetSingleItemResponse<String> createItem(CreateItemRequest request);

    BaseResponse updateItem(UpdateItemRequest request);

    BaseResponse deleteItem(DeleteDataRequest request);

    BaseResponse buyItem(BuyItemRequest request);

    BaseResponse listItemToMarket(ListItemToMarketRequest request);

    BaseResponse cancelListItemToMarket(CancelListingItemRequest request);

    BaseResponse likeItem(String itemUuid, HeaderInfo headerInfo);

    BaseResponse unlikeItem(String itemUuid, HeaderInfo headerInfo);
}
