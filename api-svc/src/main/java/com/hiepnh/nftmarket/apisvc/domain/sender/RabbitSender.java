package com.hiepnh.nftmarket.apisvc.domain.sender;

import com.hiepnh.nftmarket.apisvc.domain.rabbitmq.AuctionEventEntity;
import com.hiepnh.nftmarket.apisvc.domain.rabbitmq.EventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RabbitSender implements ISender{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.transaction.queue}")
    private String transactionQueue;

    @Value("${rabbitmq.transaction.routing_key}")
    private String transactionRoutingKey;

    @Value("${rabbitmq.auction.queue}")
    private String auctionQueue;

    @Value("${rabbitmq.auction.routing_key}")
    private String auctionRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishCreateAuctionEvent(Object obj){
        EventEntity event = new AuctionEventEntity("create", obj);
        rabbitTemplate.convertAndSend(exchange, auctionRoutingKey, event);
        logger.info("=>publishCreateAuctionEvent type: {create}, auction: {}", obj);
    }
}
