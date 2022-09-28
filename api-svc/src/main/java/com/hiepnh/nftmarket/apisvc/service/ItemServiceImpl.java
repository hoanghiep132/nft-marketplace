package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.common.Constant;
import com.hiepnh.nftmarket.apisvc.common.EnumCommon;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.domain.request.*;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetArrayResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.*;
import com.hiepnh.nftmarket.apisvc.entites.dto.ItemDetailDTO;
import com.hiepnh.nftmarket.apisvc.entites.model.ItemBidHistoryModel;
import com.hiepnh.nftmarket.apisvc.entites.model.ItemBuyingHistoryModel;
import com.hiepnh.nftmarket.apisvc.entites.model.NftItemContractModel;
import com.hiepnh.nftmarket.apisvc.repository.*;
import com.hiepnh.nftmarket.apisvc.utils.AppUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService{

    @Value("${contract.collection_default.uuid}")
    private String collectionDefaultUuid;

    private final ItemRepository itemRepository;

    private final CollectionRepository collectionRepository;

    private final BuyTransactionRepository buyTransactionRepository;

    private final FavoriteItemRepository favoriteItemRepository;

    private final ListingMarketRepository listingMarketRepository;

    private final AuctionRepository auctionRepository;

    private final BidTransactionRepository bidTransactionRepository;

    public ItemServiceImpl(ItemRepository itemRepository, CollectionRepository collectionRepository, BuyTransactionRepository buyTransactionRepository, FavoriteItemRepository favoriteItemRepository, ListingMarketRepository listingMarketRepository, AuctionRepository auctionRepository, BidTransactionRepository bidTransactionRepository) {
        this.itemRepository = itemRepository;
        this.collectionRepository = collectionRepository;
        this.buyTransactionRepository = buyTransactionRepository;
        this.favoriteItemRepository = favoriteItemRepository;
        this.listingMarketRepository = listingMarketRepository;
        this.auctionRepository = auctionRepository;
        this.bidTransactionRepository = bidTransactionRepository;
    }

    @Override
    public GetArrayResponse<ItemEntity> searchItem(String text, String collectionUuid, EnumCommon.OrderSort orderSort,
                                                   Integer status, Double min, Double max, int page, int size, HeaderInfo headerInfo) {
        GetArrayResponse<ItemEntity> response = new GetArrayResponse<>();

        Pageable pageable = PageRequest.of(page-1, size);
        Page<ItemEntity> itemPage;

        switch (orderSort){
            case LOW2HIGH:
                itemPage = itemRepository.searchItemAscPrice(text.toLowerCase(), collectionUuid, status, min, max, pageable);
                break;
            case HIGH2LOW:
                itemPage = itemRepository.searchItemDescPrice(text.toLowerCase(), collectionUuid, status, min, max, pageable);
                break;
            case MOST_FAVORITE:
                itemPage = itemRepository.searchItemDescFavorite(text.toLowerCase(), collectionUuid, status, min, max, pageable);
                break;
            default:
                itemPage = itemRepository.searchItemRecently(text.toLowerCase(), collectionUuid, status, min, max, pageable);
        }

        response.setRows(itemPage.getContent());
        response.setTotal(itemPage.getTotalElements());
        response.setSuccess();
        return response;
    }

    @Override
    public GetArrayResponse<ItemEntity> searchItem(String text, String collectionUuid, EnumCommon.OrderSort orderSort, Integer status, Double min, Double max, int page, int size) {
        GetArrayResponse<ItemEntity> response = new GetArrayResponse<>();

        Pageable pageable = PageRequest.of(page-1, size);
        Page<ItemEntity> itemPage;

        switch (orderSort){
            case LOW2HIGH:
                itemPage = itemRepository.searchItemAscPrice(text.toLowerCase(), collectionUuid, status, min, max, pageable);
                break;
            case HIGH2LOW:
                itemPage = itemRepository.searchItemDescPrice(text.toLowerCase(), collectionUuid, status, min, max, pageable);
                break;
            case MOST_FAVORITE:
                itemPage = itemRepository.searchItemDescFavorite(text.toLowerCase(), collectionUuid, status, min, max, pageable);
                break;
            default:
                itemPage = itemRepository.searchItemRecently(text.toLowerCase(), collectionUuid, status, min, max, pageable);
        }

        response.setRows(itemPage.getContent());
        response.setTotal(itemPage.getTotalElements());
        response.setSuccess();
        return response;
    }

    @Override
    public GetArrayResponse<ItemEntity> getItemFavoriteList(int page, int size, HeaderInfo headerInfo) {
        GetArrayResponse<ItemEntity> response = new GetArrayResponse<>();
        Pageable pageable = PageRequest.of(page-1, size);

        Page<FavoriteItemEntity> favoriteItemPage = favoriteItemRepository.findByUser(headerInfo.getUsername(), pageable);

        List<ItemEntity> itemList = favoriteItemPage.stream()
                .map(FavoriteItemEntity::getItem)
                .collect(Collectors.toList());

        response.setRows(itemList);
        response.setTotal(favoriteItemPage.getTotalElements());
        response.setSuccess();
        return response;
    }

    @Override
    public GetArrayResponse<ItemEntity> getOwnerItemList(int page, int size, HeaderInfo headerInfo) {
        GetArrayResponse<ItemEntity> response = new GetArrayResponse<>();
        Pageable pageable = PageRequest.of(page-1, size);

        Page<ItemEntity> itemPage = itemRepository.findByOwner(headerInfo.getUsername(), pageable);

        response.setRows(itemPage.getContent());
        response.setTotal(itemPage.getTotalElements());
        response.setSuccess();
        return response;
    }

    @Override
    public GetSingleItemResponse<ItemDetailDTO> findItemByUuid(String uuid, HeaderInfo headerInfo) {
        GetSingleItemResponse<ItemDetailDTO> response = new GetSingleItemResponse<>();

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(uuid);
        if(!itemOptional.isPresent()){
            response.setResult(-1, "Không tồn tại");
            return response;
        }

        ItemDetailDTO itemDetailDTO = new ItemDetailDTO();
        itemDetailDTO.setUuid(itemOptional.get().getUuid());
        itemDetailDTO.setName(itemOptional.get().getName());
        itemDetailDTO.setDescription(itemOptional.get().getDescription());
        itemDetailDTO.setPrice(itemOptional.get().getPrice());
        itemDetailDTO.setUuid(itemOptional.get().getUuid());
        itemDetailDTO.setStatus(itemOptional.get().getStatus());
        itemDetailDTO.setImage(itemOptional.get().getImageUrl());
        itemDetailDTO.setCollection(itemOptional.get().getCollection());
        itemDetailDTO.setOwner(itemOptional.get().getOwner());

        NftItemContractModel nftItemContract = new NftItemContractModel();
        nftItemContract.setAddress(itemOptional.get().getCollection().getContractAddress());
        nftItemContract.setTokenId(itemOptional.get().getTokenId());
        itemDetailDTO.setContractDetail(nftItemContract);

        Optional<FavoriteItemEntity> favoriteItemOptional = favoriteItemRepository.findFavorite(uuid, headerInfo.getUsername());
        itemDetailDTO.setIsFavorite(favoriteItemOptional.isPresent());


        List<BuyTransactionEntity> transactionList = buyTransactionRepository.getAllByItem(uuid);
        List<ItemBuyingHistoryModel> history = transactionList.stream().map(item -> {
            ItemBuyingHistoryModel historyModel = new ItemBuyingHistoryModel();
            historyModel.setId(item.getId());
            historyModel.setPrice(item.getPrice());
            historyModel.setAddress(item.getCreateBy());
            long createAt = item.getCreateAt();
            historyModel.setTime(AppUtils.formatLongToString(createAt, "dd/MM/yyyy HH:mm:ss"));
            return historyModel;
        }).collect(Collectors.toList());
        itemDetailDTO.setHistory(history);

        List<ItemEntity> others = itemRepository.searchOthers(itemOptional.get().getCollection().getUuid(), itemOptional.get().getStatus(), itemOptional.get().getUuid());
        itemDetailDTO.setOthers(others);

        if(itemOptional.get().getStatus() == Constant.ItemStatus.LISTING){
            Optional<ListingMarketEntity> listingMarketOptional = listingMarketRepository.findByItemUuid(uuid);
            if(!listingMarketOptional.isPresent()){
                itemDetailDTO.setStatus(Constant.ItemStatus.CREATED);
            }else {
                itemDetailDTO.setItemMarketId(listingMarketOptional.get().getItemMarketId());
            }
        }else if(itemOptional.get().getStatus() == Constant.ItemStatus.AUCTION){
            Optional<AuctionEntity> auctionOptional = auctionRepository.findByItemUuid(uuid);
            if(!auctionOptional.isPresent()){
                itemDetailDTO.setStatus(Constant.ItemStatus.CREATED);
            }else {
                itemDetailDTO.setItemActionId(auctionOptional.get().getItemAuctionId());

                itemDetailDTO.setEndAt(auctionOptional.get().getEndTime());
                long endTimeLong = auctionOptional.get().getEndTime();
                String endTime = AppUtils.formatLongToString(endTimeLong, "dd/MM/yyyy HH:mm:ss");
                itemDetailDTO.setEndTime(endTime);

                long remainTime = endTimeLong - auctionOptional.get().getStartTime();
                itemDetailDTO.setRemainTime(remainTime);

                List<BidTransactionEntity> bidTransactionEntityList = bidTransactionRepository.findAllByItemUuid(uuid);
                if(bidTransactionEntityList == null || bidTransactionEntityList.isEmpty()){
                    itemDetailDTO.setBidHistory(new ArrayList<>());
                    itemDetailDTO.setPrice(auctionOptional.get().getStartPrice());
                }else {
                    List<ItemBidHistoryModel> bidHistory = bidTransactionEntityList.stream().map(item -> ItemBidHistoryModel.builder()
                            .address(item.getCreateBy())
                            .price(item.getPrice())
                            .time(AppUtils.formatLongToString(item.getCreateAt(), "dd/MM/yyyy HH:mm:ss"))
                            .build()).collect(Collectors.toList());
                    itemDetailDTO.setBidHistory(bidHistory);
                    if(!bidHistory.isEmpty()){
                        itemDetailDTO.setPrice(bidHistory.get(0).getPrice());
                    }
                }
            }
        }

        response.setItem(itemDetailDTO);
        response.setSuccess();
        return response;
    }

    @Override
    public GetSingleItemResponse<ItemDetailDTO> findItemByUuid(String uuid) {
        GetSingleItemResponse<ItemDetailDTO> response = new GetSingleItemResponse<>();

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(uuid);
        if(!itemOptional.isPresent()){
            response.setResult(-1, "Không tồn tại");
            return response;
        }

        ItemDetailDTO itemDetailDTO = new ItemDetailDTO();
        itemDetailDTO.setUuid(itemOptional.get().getUuid());
        itemDetailDTO.setName(itemOptional.get().getName());
        itemDetailDTO.setDescription(itemOptional.get().getDescription());
        itemDetailDTO.setPrice(itemOptional.get().getPrice());
        itemDetailDTO.setUuid(itemOptional.get().getUuid());
        itemDetailDTO.setStatus(itemOptional.get().getStatus());
        itemDetailDTO.setImage(itemOptional.get().getImageUrl());
        itemDetailDTO.setCollection(itemOptional.get().getCollection());
        itemDetailDTO.setOwner(itemOptional.get().getOwner());

        NftItemContractModel nftItemContract = new NftItemContractModel();
        nftItemContract.setAddress(itemOptional.get().getCollection().getContractAddress());
        nftItemContract.setTokenId(itemOptional.get().getTokenId());
        itemDetailDTO.setContractDetail(nftItemContract);

        List<BuyTransactionEntity> transactionList = buyTransactionRepository.getAllByItem(uuid);
        List<ItemBuyingHistoryModel> history = transactionList.stream().map(item -> {
            ItemBuyingHistoryModel historyModel = new ItemBuyingHistoryModel();
            historyModel.setId(item.getId());
            historyModel.setPrice(item.getPrice());
            historyModel.setAddress(item.getCreateBy());
            long createAt = item.getCreateAt();
            historyModel.setTime(AppUtils.formatLongToString(createAt, "dd/MM/yyyy HH:mm:ss"));
            return historyModel;
        }).collect(Collectors.toList());
        itemDetailDTO.setHistory(history);

        List<ItemEntity> others = itemRepository.searchOthers(itemOptional.get().getCollection().getUuid(), itemOptional.get().getStatus(), itemOptional.get().getUuid());
        itemDetailDTO.setOthers(others);

        if(itemOptional.get().getStatus() == Constant.ItemStatus.LISTING){
            Optional<ListingMarketEntity> listingMarketOptional = listingMarketRepository.findByItemUuid(uuid);
            if(!listingMarketOptional.isPresent()){
                itemDetailDTO.setStatus(Constant.ItemStatus.CREATED);
            }else {
                itemDetailDTO.setItemMarketId(listingMarketOptional.get().getItemMarketId());
            }
        }else if(itemOptional.get().getStatus() == Constant.ItemStatus.AUCTION){
            Optional<AuctionEntity> auctionOptional = auctionRepository.findByItemUuid(uuid);
            if(!auctionOptional.isPresent()){
                itemDetailDTO.setStatus(Constant.ItemStatus.CREATED);
            }else {
                itemDetailDTO.setItemActionId(auctionOptional.get().getItemAuctionId());

                itemDetailDTO.setEndAt(auctionOptional.get().getEndTime());
                long endTimeLong = auctionOptional.get().getEndTime();
                String endTime = AppUtils.formatLongToString(endTimeLong, "dd/MM/yyyy HH:mm:ss");
                itemDetailDTO.setEndTime(endTime);

                long remainTime = endTimeLong - auctionOptional.get().getStartTime();
                itemDetailDTO.setRemainTime(remainTime);

                List<BidTransactionEntity> bidTransactionEntityList = bidTransactionRepository.findAllByItemUuid(uuid);
                if(bidTransactionEntityList == null || bidTransactionEntityList.isEmpty()){
                    itemDetailDTO.setBidHistory(new ArrayList<>());
                    itemDetailDTO.setPrice(auctionOptional.get().getStartPrice());
                }else {
                    List<ItemBidHistoryModel> bidHistory = bidTransactionEntityList.stream().map(item -> ItemBidHistoryModel.builder()
                            .address(item.getCreateBy())
                            .price(item.getPrice())
                            .time(AppUtils.formatLongToString(item.getCreateAt(), "dd/MM/yyyy HH:mm:ss"))
                            .build()).collect(Collectors.toList());
                    itemDetailDTO.setBidHistory(bidHistory);
                    if(!bidHistory.isEmpty()){
                        itemDetailDTO.setPrice(bidHistory.get(0).getPrice());
                    }
                }
            }
        }

        response.setItem(itemDetailDTO);
        response.setSuccess();

        return response;
    }

    @Override
    public GetSingleItemResponse<String> createItem(CreateItemRequest request) {
        GetSingleItemResponse<String> response = new GetSingleItemResponse<>();

        Optional<CollectionEntity> collectionOptional = collectionRepository.findByUuid(request.getCollectionUuid());
        if(!collectionOptional.isPresent()){
            response.setResult(-1, "Bộ sưu tập không tồn tại");
            return response;
        }

        if(!request.getCollectionUuid().equals(collectionDefaultUuid)){
            if(!collectionOptional.get().getCreateBy().equals(request.getInfo().getUsername())){
                response.setResult(-1, "Người dùng không có quyền");
                return response;
            }
        }

        String uuid = AppUtils.generateUuid();

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setUuid(uuid);
        itemEntity.setImageUrl(request.getImageUrl());
        itemEntity.setCollection(collectionOptional.get());
        itemEntity.setName(request.getName());
        itemEntity.setDescription(request.getDescription());
        itemEntity.setPrice(0.0);
        itemEntity.setStatus(Constant.ItemStatus.CREATED);
        itemEntity.setTxnHash(request.getTnxHash());
        itemEntity.setBlockHash(request.getBlockHash());
        itemEntity.setFavoriteCount(0);
        itemEntity.setTokenId(request.getTokenId());
        itemEntity.setOwner(request.getInfo().getUsername());
        itemEntity.setCreateAt(System.currentTimeMillis());
        itemEntity.setCreateBy(request.getInfo().getUsername());

        itemRepository.save(itemEntity);

        CollectionEntity collectionEntity = collectionOptional.get();
        int total = collectionEntity.getTotal();
        collectionEntity.setTotal(total+1);

        collectionRepository.save(collectionEntity);

        response.setItem(uuid);
        response.setSuccess();
        return response;
    }

    @Override
    public BaseResponse updateItem(UpdateItemRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(request.getUuid());
        if(!itemOptional.isPresent()){
            return new BaseResponse(-1, "Vật phẩm không tồn tại");
        }

        ItemEntity itemEntity = itemOptional.get();
        itemEntity.setDescription(request.getDescription());

        itemRepository.save(itemEntity);

        return response;
    }

    @Override
    public BaseResponse deleteItem(DeleteDataRequest request) {
        return null;
    }

    @Override
    public BaseResponse buyItem(BuyItemRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(request.getItemUuid());
        if(!itemOptional.isPresent()){
            return new BaseResponse(-1, "Vật phẩm không tồn tại");
        }

        Optional<ListingMarketEntity> listingMarketOptional = listingMarketRepository.findByItemUuid(request.getItemUuid());
        if(!listingMarketOptional.isPresent()){
            return new BaseResponse(-1, "Vật phẩm chưa mở bán");
        }
        if(listingMarketOptional.get().getStatus() == 0){
            return new BaseResponse(-1, "Vật phẩm chưa mở bán");
        }

        ItemEntity itemEntity = itemOptional.get();
        if(itemEntity.getStatus() != Constant.ItemStatus.LISTING){
            return new BaseResponse(-1, "Error");
        }

        BuyTransactionEntity buyTransactionEntity = new BuyTransactionEntity();
        buyTransactionEntity.setItem(itemEntity);
        buyTransactionEntity.setPrice(itemEntity.getPrice());
        buyTransactionEntity.setTxnHash(request.getTnxHash());
        buyTransactionEntity.setBlockHash(request.getBlockHash());
        buyTransactionEntity.setCreateAt(System.currentTimeMillis());
        buyTransactionEntity.setCreateBy(request.getInfo().getUsername());

        buyTransactionRepository.save(buyTransactionEntity);

        itemEntity.setStatus(Constant.ItemStatus.CREATED);
        itemEntity.setPrice(0D);
        itemEntity.setOwner(request.getInfo().getUsername());
        itemRepository.save(itemEntity);

        ListingMarketEntity listingMarket = listingMarketOptional.get();
        listingMarket.setStatus(0);
        listingMarket.setUpdateAt(System.currentTimeMillis());
        listingMarket.setCreateBy(request.getInfo().getUsername());
        listingMarketRepository.save(listingMarket);

        response.setSuccess();
        return response;
    }

    @Override
    public BaseResponse listItemToMarket(ListItemToMarketRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(request.getUuid());
        if(!itemOptional.isPresent()){
            response.setResult(-1, "Không tồn tại");
            return response;
        }

        ListingMarketEntity listingMarketEntity = new ListingMarketEntity();

        listingMarketEntity.setItem(itemOptional.get());
        listingMarketEntity.setPrice(request.getPrice());
        listingMarketEntity.setStatus(1);
        listingMarketEntity.setItemMarketId(request.getItemMarketId());
        listingMarketEntity.setBlockHash(request.getBlockHash());
        listingMarketEntity.setTnxHash(request.getTnxHash());
        listingMarketEntity.setCreateAt(System.currentTimeMillis());
        listingMarketEntity.setCreateBy(request.getInfo().getUsername());
        listingMarketEntity.setUpdateAt(System.currentTimeMillis());
        listingMarketEntity.setUpdateBy(request.getInfo().getUsername());

        listingMarketRepository.save(listingMarketEntity);

        ItemEntity itemEntity = itemOptional.get();
        itemEntity.setStatus(Constant.ItemStatus.LISTING);
        itemEntity.setPrice(request.getPrice());
        itemRepository.save(itemEntity);

        response.setSuccess();
        return response;
    }

    @Override
    public BaseResponse cancelListItemToMarket(CancelListingItemRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<ListingMarketEntity> listingMarketOptional = listingMarketRepository.findByItemUuid(request.getUuid());
        if(!listingMarketOptional.isPresent()){
            response.setResult(-1, "Không tồn tại");
            return response;
        }

        if(listingMarketOptional.get().getStatus() == 1){
            ListingMarketEntity listingMarketEntity = listingMarketOptional.get();
            listingMarketEntity.setStatus(0);
            listingMarketEntity.setUpdateBy(request.getInfo().getUsername());
            listingMarketEntity.setUpdateAt(System.currentTimeMillis());

            listingMarketRepository.save(listingMarketEntity);
        }

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(request.getUuid());
        if(!itemOptional.isPresent()){
            response.setResult(-1, "Không tồn tại");
            return response;
        }

        ItemEntity itemEntity = itemOptional.get();
        itemEntity.setStatus(Constant.ItemStatus.CREATED);

        itemRepository.save(itemEntity);

        response.setSuccess();
        return response;
    }

    @Override
    public BaseResponse likeItem(String itemUuid, HeaderInfo headerInfo) {
        BaseResponse response = new BaseResponse();

        Optional<FavoriteItemEntity> favoriteOptional = favoriteItemRepository.findFavorite(itemUuid, headerInfo.getUsername());
        if(favoriteOptional.isPresent()){
            response.setSuccess();
            return response;
        }

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(itemUuid);
        if(!itemOptional.isPresent()){
            response.setResult(-1, "Không tồn tại");
            return response;
        }
        ItemEntity itemEntity = itemOptional.get();

        FavoriteItemEntity favoriteItemEntity = new FavoriteItemEntity();
        favoriteItemEntity.setItem(itemEntity);
        favoriteItemEntity.setCreateAt(System.currentTimeMillis());
        favoriteItemEntity.setCreateBy(headerInfo.getUsername());

        favoriteItemRepository.save(favoriteItemEntity);

        itemEntity.setFavoriteCount(itemEntity.getFavoriteCount() + 1);
        itemRepository.save(itemEntity);

        response.setSuccess();
        return response;
    }

    @Override
    public BaseResponse unlikeItem(String itemUuid, HeaderInfo headerInfo) {
        BaseResponse response = new BaseResponse();

        Optional<FavoriteItemEntity> favoriteOptional = favoriteItemRepository.findFavorite(itemUuid, headerInfo.getUsername());
        if(!favoriteOptional.isPresent()){
            response.setSuccess();
            return response;
        }

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(itemUuid);
        if(!itemOptional.isPresent()){
            response.setResult(-1, "Không tồn tại");
            return response;
        }
        ItemEntity itemEntity = itemOptional.get();

        favoriteItemRepository.deleteById(favoriteOptional.get().getId());

        itemEntity.setFavoriteCount(itemEntity.getFavoriteCount() - 1);
        itemRepository.save(itemEntity);

        response.setSuccess();
        return response;
    }
}
