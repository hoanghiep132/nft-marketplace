package com.hiepnh.nftmarket.coresvc.domain.rabbit;

import com.hiepnh.nftmarket.coresvc.common.Constant;
import com.hiepnh.nftmarket.coresvc.domain.dto.AuctionData;
import com.hiepnh.nftmarket.coresvc.entites.AuctionEventDataEntity;
import com.hiepnh.nftmarket.coresvc.helper.JsonHelper;
import com.hiepnh.nftmarket.coresvc.repository.AuctionEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RabbitConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AuctionEventRepository auctionEventRepository;

    public RabbitConsumer(AuctionEventRepository auctionEventRepository) {

        this.auctionEventRepository = auctionEventRepository;
    }

    @RabbitListener(queues = "${rabbitmq.auction.queue}")
    public void receivedCreateAuction(AuctionEventEntity auctionEvent) throws Exception {
        logger.info("Event from auction queue : {}", auctionEvent);

        switch (auctionEvent.getType()){
            case "create":
                createAuctionScheduledTask(auctionEvent);
                break;
            default:
                logger.info("default");
        }
    }

    @RabbitListener(queues = "${rabbitmq.transaction.queue}")
    public void consumerTransactionEvent() {
        logger.info("Event from transaction queue");
    }


    private void createAuctionScheduledTask(AuctionEventEntity auctionEvent){
        CreateAuctionEventData auctionData;
        try{
            Map<String, Object> dataMap = (Map<String, Object>) auctionEvent.getAuction();
            String dataJson = JsonHelper.convertToJson(dataMap);
            auctionData = JsonHelper.convertJsonToObject(dataJson, CreateAuctionEventData.class);
        }catch (Exception ex){
            auctionData = new CreateAuctionEventData();
        }

//        if(auctionData.getStartTime() != null && auctionData.getStartTime() != 0){
//            Optional<AuctionEventDataEntity> auctionEventOptional = auctionEventRepository.findById(auctionData.getStartTime());
//            AuctionEventDataEntity auctionEventDataEntity;
//            AuctionData startAuction = new AuctionData();
//            startAuction.setType(Constant.AuctionMode.START);
//            startAuction.setItemUuid(auctionData.getItemUuid());
//            if(auctionEventOptional.isPresent()){
//                auctionEventDataEntity = auctionEventOptional.get();
//                auctionEventDataEntity.getData().add(startAuction);
//            }else {
//                auctionEventDataEntity = new AuctionEventDataEntity();
//                auctionEventDataEntity.setTime(auctionData.getStartTime());
//
//                List<AuctionData> auctionDataList = new ArrayList<>();
//                auctionDataList.add(startAuction);
//                auctionEventDataEntity.setData(auctionDataList);
//            }
//            auctionEventRepository.save(auctionEventDataEntity);
//        }

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

}
