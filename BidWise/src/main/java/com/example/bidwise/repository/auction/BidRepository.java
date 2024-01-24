package com.example.bidwise.repository.auction;

import com.example.bidwise.entity.auction.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<BidEntity, Long> {
}
