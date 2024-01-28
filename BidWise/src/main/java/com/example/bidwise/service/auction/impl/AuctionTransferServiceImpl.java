package com.example.bidwise.service.auction.impl;

import com.example.bidwise.entity.product.FinishedProductEntity;
import com.example.bidwise.entity.product.ProductEntity;
import com.example.bidwise.repository.product.FinishedProductRepository;
import com.example.bidwise.repository.product.ProductRepository;
import com.example.bidwise.service.auction.AuctionTransferService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuctionTransferServiceImpl implements AuctionTransferService {

    private final ProductRepository productRepository;
    private final FinishedProductRepository finishedProductRepository;


    // Run every dat at 1 AM
    @Scheduled(cron = "0 0 1 * * ?")
    @Override
    public void transferFinishedAuctions() {
        List<ProductEntity> products = productRepository.findAllByFinishedAuction(true);


        for (ProductEntity p : products) {
            FinishedProductEntity finishedProduct = new FinishedProductEntity();
            finishedProduct.setName(p.getName());
            finishedProduct.setDescription(p.getDescription());
            finishedProduct.setPrice(p.getPrice());
            finishedProduct.setImage(p.getImage());
            finishedProduct.setCategory(p.getCategory());
            finishedProduct.setAuction(p.getAuction());
            finishedProduct.setUserOwner(p.getUserOwner());
            finishedProduct.setHighestBid(p.getHighestBid());
            finishedProduct.setFinishedAuction(p.isFinishedAuction());
            productRepository.delete(p);
            finishedProductRepository.save(finishedProduct);
        }

    }
}
