package com.example.bidwise.repository.product;

import com.example.bidwise.entity.product.FinishedProductEntity;
import com.example.bidwise.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinishedProductRepository extends JpaRepository<FinishedProductEntity, Long> {

    List<FinishedProductEntity> findAllByUserOwner(UserEntity userOwner);
}
