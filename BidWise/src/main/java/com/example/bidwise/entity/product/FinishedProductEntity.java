package com.example.bidwise.entity.product;

import com.example.bidwise.entity.auction.AuctionEntity;
import com.example.bidwise.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "finished_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinishedProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "image")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToOne
    private AuctionEntity auction;

    @ManyToOne
    private UserEntity userOwner;

    private int highestBid;

    private boolean finishedAuction;


}
