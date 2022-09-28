package com.hiepnh.nftmarket.coresvc.service;

import com.hiepnh.nftmarket.coresvc.common.Constant;
import com.hiepnh.nftmarket.coresvc.domain.dto.AuctionData;
import com.hiepnh.nftmarket.coresvc.domain.rabbit.CreateAuctionEventData;
import com.hiepnh.nftmarket.coresvc.entites.AuctionEventDataEntity;
import com.hiepnh.nftmarket.coresvc.repository.AuctionEventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionServiceImpl extends BaseService implements AuctionService{

    private final AuctionEventRepository auctionEventRepository;

    public AuctionServiceImpl(AuctionEventRepository auctionEventRepository) {
        this.auctionEventRepository = auctionEventRepository;
    }

    @Override
    public void processCreateAuction(CreateAuctionEventData data) {
        if(data.getStartTime() != null && data.getStartTime() != 0){
            long startAtSecond = data.getStartTime() / 1000;

            updateData(data.getItemUuid(), startAtSecond, Constant.AuctionMode.START);
        }

        long endAtSecond = data.getEndTime() / 1000;
        updateData(data.getItemUuid(), endAtSecond, Constant.AuctionMode.END);

    }

    private void updateData(String itemUuid, long time, int modeType){
        Optional<AuctionEventDataEntity> auctionEventOptional = auctionEventRepository.findById(time);
        AuctionEventDataEntity auctionEventEntity;
        if(auctionEventOptional.isPresent()){
            auctionEventEntity = auctionEventOptional.get();
            AuctionData auctionData = AuctionData.builder()
                    .itemUuid(itemUuid)
                    .type(modeType)
                    .build();

            auctionEventEntity.getData().add(auctionData);

        }else {
            auctionEventEntity = new AuctionEventDataEntity();
            auctionEventEntity.setTime(time);
            List<AuctionData> auctionList = new ArrayList<>();
            AuctionData auctionData = AuctionData.builder()
                    .itemUuid(itemUuid)
                    .type(modeType)
                    .build();

            auctionList.add(auctionData);

            auctionEventEntity.setData(auctionList);
        }
        auctionEventRepository.save(auctionEventEntity);
    }
}
