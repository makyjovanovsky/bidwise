package com.example.bidwise.entity.auction;

import com.example.bidwise.entity.auction.AuctionEntity;
import com.example.bidwise.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private AuctionEntity auction;

    public BidEntity(int price, UserEntity user, AuctionEntity auction) {
        this.price = price;
        this.user = user;
        this.auction = auction;
    }
}
