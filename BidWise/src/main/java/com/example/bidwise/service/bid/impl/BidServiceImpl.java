package com.example.bidwise.service.bid.impl;

import com.example.bidwise.entity.auction.AuctionEntity;
import com.example.bidwise.entity.auction.BidEntity;
import com.example.bidwise.entity.product.ProductEntity;
import com.example.bidwise.entity.user.UserEntity;
import com.example.bidwise.repository.auction.AuctionRepository;
import com.example.bidwise.repository.auction.BidRepository;
import com.example.bidwise.repository.product.ProductRepository;
import com.example.bidwise.repository.user.UserRepository;
import com.example.bidwise.service.bid.BidService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public boolean addBid(Long userId, Long productId, int price) throws Exception {

        ProductEntity product = productRepository.findById(productId).orElseThrow(Exception::new);
        UserEntity user = userRepository.findById(userId).orElseThrow(Exception::new);
        AuctionEntity auction = auctionRepository.findByProduct(product);

        if (!LocalDateTime.now().isAfter(auction.getTimeOfFinish()) && user.getBalance() >= price) {

            if (product.getUserOwner().getId() == userId) {
                return false;
            }

            if (auction.getBids() == null) {
                auction.setBids(new ArrayList<>());
            }

            boolean flag = false;

            for (BidEntity b : auction.getBids()) {
                if (b.getPrice() >= price) {
                    flag = true;
                }
            }

            if (!flag) {
                BidEntity bid = new BidEntity(price, user, auction);
                bidRepository.save(bid);
                auction.getBids().add(bid);
                auctionRepository.save(auction);

                product.setHighestBid(price);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

}
