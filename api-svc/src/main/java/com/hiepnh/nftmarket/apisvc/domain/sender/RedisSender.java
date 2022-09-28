package com.hiepnh.nftmarket.apisvc.domain.sender;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.hiepnh.nftmarket.apisvc.domain.rabbitmq.AuctionEventEntity;
import com.hiepnh.nftmarket.apisvc.domain.rabbitmq.EventEntity;
import com.hiepnh.nftmarket.apisvc.utils.JsonUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisSender implements ISender{

    private final RedisTemplate<String, Object> redisTemplate;

    private final ChannelTopic channelTopic;

    public RedisSender(RedisTemplate<String, Object> redisTemplate, ChannelTopic channelTopic) {
        this.redisTemplate = redisTemplate;
        this.channelTopic = channelTopic;
    }

    @Override
    public void publishCreateAuctionEvent(Object obj) {
        EventEntity event = new AuctionEventEntity("create", obj);

        String json = JsonUtils.convertToJson(event);
        redisTemplate.convertAndSend(channelTopic.getTopic(), json);
    }
}
