package com.example.bidwise.repository.product;

import com.example.bidwise.entity.product.CategoryEntity;
import com.example.bidwise.entity.product.ProductEntity;
import com.example.bidwise.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findAllByCategoryAndFinishedAuction(CategoryEntity category, boolean finishedAuction, Pageable pageable);

    Page<ProductEntity> findAllByFinishedAuction(boolean finishedAuction, Pageable pageable);

    List<ProductEntity> findAllByFinishedAuction(boolean finishedAuction);

    List<ProductEntity> findAllByUserOwner(UserEntity user);
}
