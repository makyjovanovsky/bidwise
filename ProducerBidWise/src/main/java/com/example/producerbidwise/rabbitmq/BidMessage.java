package com.example.producerbidwise.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BidMessage {

    private Long userId;

    private Long productId;

    private int price;

    private String response;

    private String responseQueueName;
}
