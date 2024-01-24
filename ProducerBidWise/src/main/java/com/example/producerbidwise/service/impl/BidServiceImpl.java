package com.example.producerbidwise.service.impl;

import com.example.producerbidwise.rabbitmq.BidMessage;
import com.example.producerbidwise.service.BidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {

    private final AmqpTemplate amqpTemplate;
    private final RabbitAdmin rabbitAdmin;
    private final ObjectMapper objectMapper;

    @Override
    public String addBid(Long userId, Long productId, int price) throws IOException {
        String responseQueueName = "responseQueue." + UUID.randomUUID().toString();
        rabbitAdmin.declareQueue(new Queue(responseQueueName, true));

        BidMessage bidMessage = new BidMessage(userId, productId, price, "", responseQueueName);
        String message = objectMapper.writeValueAsString(bidMessage);
        amqpTemplate.convertAndSend("q.product" + productId, message);

        Message responseMessage;

        while ((responseMessage = amqpTemplate.receive(responseQueueName)) == null) {
        }

        BidMessage response = objectMapper.readValue(responseMessage.getBody(), BidMessage.class);
        return response.getResponse();
    }

}


