package com.example.bidwise.service.bid;

public interface BidService {

    boolean addBid(Long userId, Long productId, int price) throws Exception;
}
