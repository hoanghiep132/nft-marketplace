package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.common.Constant;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateCollectionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.DeleteDataRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.UpdateCollectionRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetArrayResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.CollectionEntity;
import com.hiepnh.nftmarket.apisvc.entites.FavoriteItemEntity;
import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.CollectionContractDTO;
import com.hiepnh.nftmarket.apisvc.entites.dto.CollectionDTO;
import com.hiepnh.nftmarket.apisvc.entites.dto.CollectionDetailDTO;
import com.hiepnh.nftmarket.apisvc.entites.dto.ItemDTO;
import com.hiepnh.nftmarket.apisvc.repository.CollectionRepository;
import com.hiepnh.nftmarket.apisvc.repository.FavoriteItemRepository;
import com.hiepnh.nftmarket.apisvc.repository.ItemRepository;
import com.hiepnh.nftmarket.apisvc.utils.AppUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImpl extends BaseService implements CollectionService{

    @Value("${contract.collection_default.address}")
    private String collectionDefaultAddress;

    @Value("${contract.collection_default.uuid}")
    private String collectionDefaultUuid;

    @Value("${contract.collection_default.name}")
    private String collectionDefaultName;

    @Value("${contract.collection_default.symbol}")
    private String collectionDefaultSymbol;

    private final CollectionRepository collectionRepository;

    private final ItemRepository itemRepository;

    private final FavoriteItemRepository favoriteItemRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository, ItemRepository itemRepository, FavoriteItemRepository favoriteItemRepository) {
        this.collectionRepository = collectionRepository;
        this.itemRepository = itemRepository;
        this.favoriteItemRepository = favoriteItemRepository;
    }

    @Override
    public GetArrayResponse<CollectionEntity> searchCollection(String text, String category, int page, int size, HeaderInfo headerInfo) {
        GetArrayResponse<CollectionEntity> response = new GetArrayResponse<>();

        Pageable pageable = PageRequest.of(page-1, size);
        Page<CollectionEntity> collectionPage = collectionRepository.searchCollection(text, category, pageable);

        response.setTotal(collectionPage.getTotalElements());
        response.setRows(collectionPage.getContent());
        response.setSuccess();

        return response;
    }

    @Override
    public GetArrayResponse<CollectionEntity> searchCollection(String text, String category, int page, int size) {
        GetArrayResponse<CollectionEntity> response = new GetArrayResponse<>();

        Pageable pageable = PageRequest.of(page-1, size);
        Page<CollectionEntity> collectionPage = collectionRepository.searchCollection(text, category, pageable);

        response.setTotal(collectionPage.getTotalElements());
        response.setRows(collectionPage.getContent());
        response.setSuccess();

        return response;
    }

    @Override
    public GetSingleItemResponse<CollectionDetailDTO> getCollectionDetail(String uuid, int page, int size, HeaderInfo headerInfo) {
        GetSingleItemResponse<CollectionDetailDTO> response = new GetSingleItemResponse<>();

        Optional<CollectionEntity> collectionEntityOptional = collectionRepository.findByUuid(uuid);
        if(!collectionEntityOptional.isPresent()){
            response.setResult(-1, "Bộ sưu tập không tồn tại");
            return response;
        }

        CollectionEntity collectionEntity = collectionEntityOptional.get();

        CollectionDetailDTO collectionDetailDTO = new CollectionDetailDTO();
        collectionDetailDTO.setUuid(collectionEntity.getUuid());
        collectionDetailDTO.setName(collectionEntity.getName());
        collectionDetailDTO.setSymbol(collectionEntity.getSymbol());
        collectionDetailDTO.setDescription(collectionEntity.getDescription());
        collectionDetailDTO.setImage(collectionEntity.getImage());
        collectionDetailDTO.setBannerImg(collectionEntity.getBannerImg());
        collectionDetailDTO.setCategories(Arrays.asList(collectionEntity.getCategories().split(",")));
        collectionDetailDTO.setCreateBy(collectionEntity.getCreateBy());
        collectionDetailDTO.setCreateAt(AppUtils.formatLongToString(collectionEntity.getCreateAt(), "dd/MM/yyyy HH:mm:ss"));

        List<FavoriteItemEntity> favoriteItemEntityList = favoriteItemRepository.findFavoriteByCollectionUuid(uuid, headerInfo.getUsername());

        Pageable pageable = PageRequest.of(page-1, size);
        Page<ItemEntity> itemPage = itemRepository.searchByCollectionUuid(uuid, pageable);
        List<ItemDTO> items = itemPage.stream()
                .map(item -> {
                    ItemDTO itemDTO = new ItemDTO();
                    itemDTO.setUuid(item.getUuid());
                    itemDTO.setName(item.getName());
                    itemDTO.setDescription(item.getDescription());
                    itemDTO.setPrice(item.getPrice());
                    itemDTO.setStatus(item.getStatus());
                    itemDTO.setImageUrl(item.getImageUrl());

                    boolean isMatch = favoriteItemEntityList.stream().anyMatch(fav -> fav.getItem().getUuid().equals(item.getUuid()));
                    itemDTO.setIsFavorite(isMatch);

                    return itemDTO;
                })
                .collect(Collectors.toList());
        collectionDetailDTO.setItems(items);

        response.setItem(collectionDetailDTO);
        response.setSuccess();
        return response;
    }

    @Override
    public GetSingleItemResponse<CollectionDetailDTO> getCollectionDetail(String uuid, int page, int size) {
        GetSingleItemResponse<CollectionDetailDTO> response = new GetSingleItemResponse<>();

        Optional<CollectionEntity> collectionEntityOptional = collectionRepository.findByUuid(uuid);
        if(!collectionEntityOptional.isPresent()){
            response.setResult(-1, "Bộ sưu tập không tồn tại");
            return response;
        }

        CollectionEntity collectionEntity = collectionEntityOptional.get();

        CollectionDetailDTO collectionDetailDTO = new CollectionDetailDTO();
        collectionDetailDTO.setUuid(collectionEntity.getUuid());
        collectionDetailDTO.setName(collectionEntity.getName());
        collectionDetailDTO.setSymbol(collectionEntity.getSymbol());
        collectionDetailDTO.setDescription(collectionEntity.getDescription());
        collectionDetailDTO.setImage(collectionEntity.getImage());
        collectionDetailDTO.setBannerImg(collectionEntity.getBannerImg());
        collectionDetailDTO.setCategories(Arrays.asList(collectionEntity.getCategories().split(",")));
        collectionDetailDTO.setCreateBy(collectionEntity.getCreateBy());
        collectionDetailDTO.setCreateAt(AppUtils.formatLongToString(collectionEntity.getCreateAt(), "dd/MM/yyyy HH:mm:ss"));

        Pageable pageable = PageRequest.of(page-1, size);
        Page<ItemEntity> itemPage = itemRepository.searchByCollectionUuid(uuid, pageable);
        List<ItemDTO> items = itemPage.stream()
                .map(item -> {
                    ItemDTO itemDTO = new ItemDTO();
                    itemDTO.setUuid(item.getUuid());
                    itemDTO.setName(item.getName());
                    itemDTO.setDescription(item.getDescription());
                    itemDTO.setPrice(item.getPrice());
                    itemDTO.setStatus(item.getStatus());
                    itemDTO.setImageUrl(item.getImageUrl());
                    return itemDTO;
                })
                .collect(Collectors.toList());
        collectionDetailDTO.setItems(items);

        response.setItem(collectionDetailDTO);
        response.setSuccess();
        return response;
    }

    @Override
    public GetArrayResponse<CollectionDTO> searchCollectionByUser(int page, int size, HeaderInfo headerInfo) {
        GetArrayResponse<CollectionDTO> response = new GetArrayResponse<>();

        Pageable pageable = PageRequest.of(page-1, size);
        Page<CollectionEntity> collectionPage = collectionRepository.findByUsername(headerInfo.getUsername(), pageable);

        List<CollectionDTO> collectionDTOList = collectionPage.stream().map(item -> {
            CollectionDTO collectionDTO = new CollectionDTO();
            collectionDTO.setUuid(item.getUuid());
            collectionDTO.setImageUrl(item.getImage());
            collectionDTO.setBannerImg(item.getBannerImg());
            collectionDTO.setDescription(item.getDescription());
            collectionDTO.setName(item.getName());
            collectionDTO.setSymbol(item.getSymbol());
            collectionDTO.setTotal(item.getTotal());
            return collectionDTO;
        }).collect(Collectors.toList());
        response.setTotal(collectionPage.getTotalElements());
        response.setRows(collectionDTOList);
        response.setSuccess();

        return response;
    }

    @Override
    public GetArrayResponse<CollectionDTO> searchCollectionByWalletAddress(int page, int size, String walletAddress) {
        GetArrayResponse<CollectionDTO> response = new GetArrayResponse<>();

        Pageable pageable = PageRequest.of(page-1, size);
        Page<CollectionEntity> collectionPage = collectionRepository.findByUsername(walletAddress, pageable);

        List<CollectionDTO> collectionDTOList = collectionPage.stream().map(item -> {
            CollectionDTO collectionDTO = new CollectionDTO();
            collectionDTO.setUuid(item.getUuid());
            collectionDTO.setImageUrl(item.getImage());
            collectionDTO.setBannerImg(item.getBannerImg());
            collectionDTO.setDescription(item.getDescription());
            collectionDTO.setName(item.getName());
            collectionDTO.setSymbol(item.getSymbol());
            collectionDTO.setTotal(item.getTotal());
            return collectionDTO;
        }).collect(Collectors.toList());
        response.setTotal(collectionPage.getTotalElements());
        response.setRows(collectionDTOList);
        response.setSuccess();

        return response;
    }

    @Override
    public GetArrayResponse<CollectionContractDTO> getOwnCollectionList(HeaderInfo headerInfo) {
        GetArrayResponse<CollectionContractDTO> response = new GetArrayResponse<>();

        List<CollectionEntity> collectionEntityList = collectionRepository.getAllByUsername(headerInfo.getUsername());

        List<CollectionContractDTO> collectionList = new ArrayList<>();

        CollectionContractDTO defaultCollection = new CollectionContractDTO();
        defaultCollection.setCollectionUuid(collectionDefaultUuid);
        defaultCollection.setContractAddress(collectionDefaultAddress);
        defaultCollection.setName(collectionDefaultName);
        defaultCollection.setSymbol(collectionDefaultSymbol);

        if(collectionEntityList.isEmpty()){
            collectionList.add(defaultCollection);
            response.setRows(collectionList);
            response.setSuccess();
            return response;
        }

        collectionList = collectionEntityList.stream().map(item -> {
            CollectionContractDTO collectionContractDTO = new CollectionContractDTO();

            collectionContractDTO.setContractAddress(item.getContractAddress());
            collectionContractDTO.setCollectionUuid(item.getUuid());
            collectionContractDTO.setName(item.getName());
            collectionContractDTO.setSymbol(item.getSymbol());

            return collectionContractDTO;
        }).collect(Collectors.toList());

        collectionList.add(defaultCollection);

        response.setRows(collectionList);
        response.setSuccess();
        return response;
    }

    @Override
    public GetSingleItemResponse<String> createCollection(CreateCollectionRequest request) {
        GetSingleItemResponse<String> response = new GetSingleItemResponse<>();

        String uuid = UUID.randomUUID().toString();

        String categories;
        if(request.getCategories() != null && !request.getCategories().isEmpty()){
            categories = String.join(",", request.getCategories());
        }else {
            categories = "";
        }

        CollectionEntity collectionEntity = new CollectionEntity();
        collectionEntity.setUuid(uuid);
        collectionEntity.setName(request.getName());
        collectionEntity.setSymbol(request.getSymbol());
        collectionEntity.setDescription(request.getDescription());
        collectionEntity.setCategories(categories);
        collectionEntity.setStatus(Constant.ItemStatus.CREATED);
        collectionEntity.setImage(request.getImageUrl());
        collectionEntity.setTxnHash(request.getTxnHash());
        collectionEntity.setBannerImg(request.getBannerUrl());
        collectionEntity.setBlockHash(request.getBlockHash());
        collectionEntity.setContractAddress(request.getContractAddress());
        collectionEntity.setCreateAt(System.currentTimeMillis());
        collectionEntity.setCreateBy(request.getInfo().getUsername());
        collectionEntity.setTotal(0);
        collectionEntity.setUpdateAt(System.currentTimeMillis());
        collectionEntity.setUpdateBy(request.getInfo().getUsername());
        try {
            collectionRepository.save(collectionEntity);
        }catch (Exception ex){
            logger.error("create collection error: ", ex);
        }

        response.setItem(uuid);
        response.setSuccess();
        return response;
    }

    @Override
    public BaseResponse updateCollection(UpdateCollectionRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<CollectionEntity> collectionEntityOptional = collectionRepository.findByUuid(request.getUuid());
        if(!collectionEntityOptional.isPresent()){
            response.setResult(-1, "Bộ sưu tập không tồn tại");
            return response;
        }

        String categories;
        if(request.getCategories() != null && !request.getCategories().isEmpty()){
            categories = String.join(",", request.getCategories());
        }else {
            categories = "";
        }

        CollectionEntity collectionEntity = collectionEntityOptional.get();
        collectionEntity.setImage(request.getImageUrl());
        collectionEntity.setBannerImg(request.getBannerUrl());
        collectionEntity.setDescription(request.getDescription());
        collectionEntity.setCategories(categories);

        collectionRepository.save(collectionEntity);

        return response;
    }

    @Override
    public BaseResponse deleteItem(DeleteDataRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<CollectionEntity> collectionEntityOptional = collectionRepository.findByUuid(request.getUuid());
        if(!collectionEntityOptional.isPresent()){
            response.setResult(-1, "Bộ sưu tập không tồn tại");
            return response;
        }

        CollectionEntity collection = collectionEntityOptional.get();
        collection.setStatus(Constant.ItemStatus.DELETED);
        collectionRepository.save(collection);

        response.setSuccess();
        return response;
    }
}
