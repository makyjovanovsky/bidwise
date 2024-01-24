package com.example.bidwise.service.auction;

import com.example.bidwise.entity.product.ProductEntity;

import java.time.LocalDateTime;

public interface AuctionService {

    void addAuction(ProductEntity product, LocalDateTime timeOfStart, LocalDateTime timeOfFinish);
}
