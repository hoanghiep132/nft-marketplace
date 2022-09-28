package com.hiepnh.nftmarket.apisvc.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfig {

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

//    @Bean
//    public TopicExchange getExchange() {
//        return new TopicExchange(exchange);
//    }

    @Bean
    public Exchange getExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    public Queue getTransactionQueue() {
        return new Queue(transactionQueue);
    }

    @Bean
    public Binding bindingTransaction() {
        return BindingBuilder
                .bind(getTransactionQueue())
                .to(getExchange())
                .with(transactionRoutingKey).noargs();
    }

    @Bean
    public Queue getAuctionQueue() {
        return new Queue(auctionQueue);
    }

    @Bean
    public Binding bindingAuction() {
        return BindingBuilder
                .bind(getAuctionQueue())
                .to(getExchange())
                .with(auctionRoutingKey).noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Jackson2JsonMessageConverter getMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory factory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

}
