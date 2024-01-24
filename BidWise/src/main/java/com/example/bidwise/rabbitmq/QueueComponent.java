package com.example.bidwise.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class QueueComponent {

    private String queueName = "q.product1";

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
