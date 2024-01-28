package com.example.bidwise.entity.auction;

import com.example.bidwise.entity.product.ProductEntity;
import com.example.bidwise.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "auction")
    @PrimaryKeyJoinColumn
    private ProductEntity product;

    private LocalDateTime timeOfStart;

    private LocalDateTime timeOfFinish;

    @ManyToOne
    private UserEntity userWinner;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BidEntity> bids;

    public AuctionEntity(LocalDateTime timeOfStart, LocalDateTime timeOfFinish) {
        this.timeOfStart = timeOfStart;
        this.timeOfFinish = timeOfFinish;
    }
}
