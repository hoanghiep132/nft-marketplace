package com.hiepnh.nftmarket.apisvc;

import com.hiepnh.nftmarket.apisvc.domain.rabbitmq.CreateAuctionEventData;
import com.hiepnh.nftmarket.apisvc.domain.sender.ISender;
import com.hiepnh.nftmarket.apisvc.domain.sender.RabbitSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {

    private final ISender sender;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Test(ISender sender) {
        this.sender = sender;
    }


    @Override
    public void run(String... args) throws Exception {
//        CreateAuctionEventData auctionEventData = CreateAuctionEventData
//                .builder()
//                .itemUuid("17290fbb-316b-461a-ab34-a08ecbf025ce")
//                .startTime(1658236524L)
//                .endTime(1658322000L)
//                .build();
//        try{
//            sender.publishCreateAuctionEvent(auctionEventData);
//        }catch (Exception ex){
//            logger.error("publish message rabbitMQ : ", ex);
//        }
    }


}
