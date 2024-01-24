package com.example.bidwise.rabbitmq;

import com.example.bidwise.service.bid.BidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@AllArgsConstructor
public class BidConsumerRegistrar {

    private final ObjectMapper objectMapper;
    private final AmqpTemplate amqpTemplate;
    private final BidService bidService;
    private final QueueComponent queueComponent;
    private final ConnectionFactory connectionFactory;
    private final AmqpAdmin amqpAdmin;

    @Bean
    @Scope("prototype")
    public BidConsumer createBidConsumer() {

        BidConsumer bidConsumer = new BidConsumer(objectMapper, bidService, amqpTemplate);
        Queue uniqueQueue = new Queue(queueComponent.getQueueName());
        amqpAdmin.declareQueue(uniqueQueue);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueComponent.getQueueName());
        container.setMessageListener(bidConsumer);
        container.start();

        return bidConsumer;
    }


}
