package com.example.producerbidwise.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface BidService {
    String addBid(Long userId, Long productId, int price) throws IOException, InterruptedException;
}
