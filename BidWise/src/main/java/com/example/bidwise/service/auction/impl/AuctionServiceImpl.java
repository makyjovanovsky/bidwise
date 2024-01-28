package com.example.bidwise.service.auction.impl;

import com.example.bidwise.entity.auction.AuctionEntity;
import com.example.bidwise.entity.auction.BidEntity;
import com.example.bidwise.entity.product.ProductEntity;
import com.example.bidwise.entity.user.UserEntity;
import com.example.bidwise.repository.auction.AuctionRepository;
import com.example.bidwise.repository.product.ProductRepository;
import com.example.bidwise.repository.user.UserRepository;
import com.example.bidwise.service.auction.AuctionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void addAuction(ProductEntity product, LocalDateTime timeOfStart, LocalDateTime timeOfFinish) {
        AuctionEntity auction = new AuctionEntity(timeOfStart, timeOfFinish);
        auctionRepository.save(auction);
        product.setAuction(auction);
        productRepository.save(product);
    }

    @Scheduled(fixedRate = 100)
    public void checkAuctionStatus() {
        List<ProductEntity> products = productRepository.findAll();

        for (ProductEntity product : products) {
            if (product.getAuction() != null && !product.isFinishedAuction()) {
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime auctionEndTime = product.getAuction().getTimeOfFinish();

                if (currentTime.isAfter(auctionEndTime)) {
                    product.setFinishedAuction(true);
                    productRepository.save(product);
                }
            }
        }
    }

    @Scheduled(fixedRate = 100)
    @Transactional
    public void findAuctionWinner() {
        List<ProductEntity> products = productRepository.findAll();

        for (ProductEntity product : products) {
            if (product.getAuction() != null && product.isFinishedAuction() && product.getAuction().getUserWinner() == null) {
                List<BidEntity> bids = product.getAuction().getBids();

                Optional<BidEntity> maxBid = bids.stream()
                        .max(Comparator.comparingInt(BidEntity::getPrice));

                maxBid.ifPresent(bidEntity -> product.getAuction().setUserWinner(bidEntity.getUser()));

                if (maxBid.isPresent()) {
                    UserEntity userWinner = maxBid.get().getUser();
                    UserEntity userOwner = product.getUserOwner();
                    if (userWinner.getBalance() >= maxBid.get().getPrice()) {
                        auctionRepository.save(product.getAuction());
                        userWinner.setBalance(userWinner.getBalance() - maxBid.get().getPrice());
                        userOwner.setBalance(userOwner.getBalance() + maxBid.get().getPrice());
                        userRepository.save(userWinner);
                        userRepository.save(userOwner);
                    }
                }

            }
        }
    }
}
