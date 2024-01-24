package com.example.bidwise.rabbitmq;

import com.example.bidwise.service.bid.BidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

@AllArgsConstructor
public class BidConsumer implements MessageListener {

    private final ObjectMapper objectMapper;
    private final BidService bidService;
    private final AmqpTemplate amqpTemplate;


    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        BidMessage bidMessage = objectMapper.readValue(message.getBody(), BidMessage.class);
//        System.out.println(this + " " + bidMessage.getProductId() + " " + bidMessage.getUserId() + " " + bidMessage.getPrice());
        try {
            boolean result = bidService.addBid(bidMessage.getUserId(), bidMessage.getProductId(), bidMessage.getPrice());
            if (result) {
                bidMessage.setResponse("Your bid has been registered!");
            } else {
                bidMessage.setResponse("Your bid has not been registered!");
            }
            amqpTemplate.convertAndSend(bidMessage.getResponseQueueName(), objectMapper.writeValueAsString(bidMessage));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
