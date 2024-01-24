package com.example.bidwise.repository.auction;

import com.example.bidwise.entity.auction.AuctionEntity;
import com.example.bidwise.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {

    AuctionEntity findByProduct(ProductEntity product);
}
