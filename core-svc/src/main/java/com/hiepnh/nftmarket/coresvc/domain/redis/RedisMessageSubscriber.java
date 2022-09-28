package com.hiepnh.nftmarket.coresvc.domain.redis;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiepnh.nftmarket.coresvc.common.Constant;
import com.hiepnh.nftmarket.coresvc.domain.dto.AuctionData;
import com.hiepnh.nftmarket.coresvc.domain.rabbit.AuctionEventEntity;
import com.hiepnh.nftmarket.coresvc.domain.rabbit.CreateAuctionEventData;
import com.hiepnh.nftmarket.coresvc.entites.AuctionEventDataEntity;
import com.hiepnh.nftmarket.coresvc.helper.JsonHelper;
import com.hiepnh.nftmarket.coresvc.repository.AuctionEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RedisMessageSubscriber implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AuctionEventRepository auctionEventRepository;

    public RedisMessageSubscriber(AuctionEventRepository auctionEventRepository) {
        this.auctionEventRepository = auctionEventRepository;
    }


    @Override
    public void onMessage(Message message, byte[] bytes) {
        logger.info("message: " + message);
        String json = message.toString();
        Map<String, Object> dataMap = JsonHelper.convertJsonToObject(json, Map.class);
        if(dataMap.containsKey("auction")){
            AuctionEventEntity auctionEvent = JsonHelper.convertJsonToObject(json, AuctionEventEntity.class);
            createAuctionScheduledTask(auctionEvent);
        }
    }


    private void createAuctionScheduledTask(AuctionEventEntity auctionEvent){
        CreateAuctionEventData auctionData = (CreateAuctionEventData) auctionEvent.getAuction();

        if(auctionData.getStartTime() != null && auctionData.getStartTime() != 0){
            Optional<AuctionEventDataEntity> auctionEventOptional = auctionEventRepository.findById(auctionData.getStartTime());
            AuctionEventDataEntity auctionEventDataEntity;
            AuctionData startAuction = new AuctionData();
            startAuction.setType(Constant.AuctionMode.START);
            startAuction.setItemUuid(auctionData.getItemUuid());
            if(auctionEventOptional.isPresent()){
                auctionEventDataEntity = auctionEventOptional.get();
                auctionEventDataEntity.getData().add(startAuction);
            }else {
                auctionEventDataEntity = new AuctionEventDataEntity();
                auctionEventDataEntity.setTime(auctionData.getStartTime());

                List<AuctionData> auctionDataList = new ArrayList<>();
                auctionDataList.add(startAuction);
                auctionEventDataEntity.setData(auctionDataList);
            }
            auctionEventRepository.save(auctionEventDataEntity);
        }

        Optional<AuctionEventDataEntity> auctionEventOptional = auctionEventRepository.findById(auctionData.getEndTime());
        AuctionEventDataEntity auctionEventDataEntity;
        AuctionData endAuction = new AuctionData();
        endAuction.setType(Constant.AuctionMode.END);
        endAuction.setItemUuid(auctionData.getItemUuid());
        if(auctionEventOptional.isPresent()){
            auctionEventDataEntity = auctionEventOptional.get();
            auctionEventDataEntity.getData().add(endAuction);
        }else {
            auctionEventDataEntity = new AuctionEventDataEntity();
            auctionEventDataEntity.setTime(auctionData.getEndTime());

            List<AuctionData> auctionDataList = new ArrayList<>();
            auctionDataList.add(endAuction);
            auctionEventDataEntity.setData(auctionDataList);
        }
        auctionEventRepository.save(auctionEventDataEntity);
    }

    private void sendEmail(String serviceName, String username){

    }
}
