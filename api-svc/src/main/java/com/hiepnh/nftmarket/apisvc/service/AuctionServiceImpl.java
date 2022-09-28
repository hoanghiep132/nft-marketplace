package com.hiepnh.nftmarket.apisvc.service;

import com.hiepnh.nftmarket.apisvc.common.Constant;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.domain.rabbitmq.CreateAuctionEventData;
import com.hiepnh.nftmarket.apisvc.domain.sender.ISender;
import com.hiepnh.nftmarket.apisvc.domain.request.CancelAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.request.CreateBidAuctionRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.AuctionEntity;
import com.hiepnh.nftmarket.apisvc.entites.BidTransactionEntity;
import com.hiepnh.nftmarket.apisvc.entites.ItemEntity;
import com.hiepnh.nftmarket.apisvc.entites.dto.AuctionDetailDTO;
import com.hiepnh.nftmarket.apisvc.repository.AuctionRepository;
import com.hiepnh.nftmarket.apisvc.repository.BidTransactionRepository;
import com.hiepnh.nftmarket.apisvc.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl extends BaseService implements AuctionService{

    private final AuctionRepository auctionRepository;

    private final ItemRepository itemRepository;

    private final BidTransactionRepository bidTransactionRepository;

    private final ISender sender;

    public AuctionServiceImpl(AuctionRepository auctionRepository, ItemRepository itemRepository, BidTransactionRepository bidTransactionRepository, ISender sender) {
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
        this.bidTransactionRepository = bidTransactionRepository;
        this.sender = sender;
    }

    @Override
    public GetSingleItemResponse<AuctionDetailDTO> getAuctionDetail(String itemUuid, HeaderInfo headerInfo) {
        GetSingleItemResponse<AuctionDetailDTO> response = new GetSingleItemResponse<>();
        AuctionDetailDTO auctionDetailDTO = new AuctionDetailDTO();

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(itemUuid);
        if(!itemOptional.isPresent()){
            response.setResult(-1, "Vật phẩm không tồn tại");
            return response;
        }

        Optional<AuctionEntity> auctionOptional = auctionRepository.findByItemUuid(itemUuid);
        if(!auctionOptional.isPresent()){
            response.setResult(-1, "Đấu giá không khả dụng");
            return response;
        }

        auctionDetailDTO.setItemEntity(itemOptional.get());
        auctionDetailDTO.setAuction(auctionOptional.get());

        Pageable pageable = PageRequest.of(0, 1000);
        Page<BidTransactionEntity> bidTransactionEntityPage = bidTransactionRepository.searchByAuction(auctionOptional.get().getUuid(), pageable);

        auctionDetailDTO.setBidList(bidTransactionEntityPage.get().collect(Collectors.toList()));

        response.setSuccess();
        response.setItem(auctionDetailDTO);
        return response;
    }

    @Override
    public BaseResponse createAuction(CreateAuctionRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<ItemEntity> itemOptional = itemRepository.findByUuid(request.getItemUuid());
        if(!itemOptional.isPresent()){
            response.setResult(-1, "Vật phẩm không tồn tại");
            return response;
        }

        AuctionEntity auctionEntity = new AuctionEntity();
        auctionEntity.setItem(itemOptional.get());
        boolean checkCurr = false;
        if(System.currentTimeMillis() - request.getStartTime() * 1000 <= 5000 ){
            checkCurr = true;
        }

        auctionEntity.setStartTime(request.getStartTime() * 1000);
        auctionEntity.setEndTime(request.getEndTime()  * 1000);

//        Date startDate = AppUtils.stringToDate2(request.getStartTime(), "dd/MM/yyyy HH:mm:ss");
//        if(startDate == null){
//            checkCurr = true;
//            auctionEntity.setStartTime(System.currentTimeMillis());
//        }else {
//            auctionEntity.setStartTime(startDate.getTime());
//        }
//
//        long endAt = AppUtils.stringToDate(request.getEndTime(), "dd/MM/yyyy HH:mm:ss").getTime();
//        auctionEntity.setEndTime(endAt);

        auctionEntity.setStartPrice(request.getStartPrice());
        auctionEntity.setCreateAt(System.currentTimeMillis());
        auctionEntity.setCreateBy(request.getInfo().getUsername());
        auctionEntity.setTxnHash(request.getTxnHash());
        auctionEntity.setBlockHash(request.getBlockHash());
        auctionEntity.setItemAuctionId(request.getAuctionMapId());
        auctionEntity.setStatus(1);

        auctionRepository.save(auctionEntity);

        ItemEntity itemEntity = itemOptional.get();
        itemEntity.setStatus(Constant.ItemStatus.AUCTION);
        itemRepository.save(itemEntity);

        CreateAuctionEventData auctionEventData = CreateAuctionEventData
                .builder()
                .itemUuid(request.getItemUuid())
                .startTime(checkCurr ? 0L : request.getStartTime())
                .endTime(request.getEndTime())
                .build();
        try{
            sender.publishCreateAuctionEvent(auctionEventData);
        }catch (Exception ex){
            logger.error("publish message rabbitMQ : ", ex);
        }

        response.setSuccess();
        return response;

    }

    @Override
    public BaseResponse bidAuction(CreateBidAuctionRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<AuctionEntity> auctionOptional = auctionRepository.findByItemUuid(request.getItemUuid());
        if(!auctionOptional.isPresent()){
            return new BaseResponse(-1, "Đấu giá không khả dụng");
        }

        BidTransactionEntity bidTransactionEntity = new BidTransactionEntity();
        bidTransactionEntity.setAuction(auctionOptional.get());
        bidTransactionEntity.setPrice(request.getPrice());
        bidTransactionEntity.setTxnHash(request.getTnxHash());
        bidTransactionEntity.setBlockHash(request.getBlockHash());
        bidTransactionEntity.setCreateBy(request.getInfo().getUsername());
        bidTransactionEntity.setCreateAt(System.currentTimeMillis());

        bidTransactionRepository.save(bidTransactionEntity);

        response.setSuccess();
        return response;
    }

    @Override
    public BaseResponse cancelAuction(CancelAuctionRequest request) {
        BaseResponse response = new BaseResponse();

        Optional<AuctionEntity> auctionOptional = auctionRepository.findByItemUuid(request.getItemUuid());
        if(!auctionOptional.isPresent()){
            return new BaseResponse(-1, "Đấu giá không khả dụng");
        }

        AuctionEntity auction = auctionOptional.get();

        if(!auction.getCreateBy().equals(request.getInfo().getUsername())){
            return new BaseResponse(-1, "Người dùng không có quyền");
        }

        auction.setStatus(0);
        auction.setUpdateAt(System.currentTimeMillis());

        auctionRepository.save(auction);

        response.setSuccess();
        return response;
    }
}
