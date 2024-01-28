package com.example.bidwise.service.product.impl;

import com.example.bidwise.entity.product.CategoryEntity;
import com.example.bidwise.entity.product.ProductEntity;
import com.example.bidwise.rabbitmq.BidConsumerRegistrar;
import com.example.bidwise.rabbitmq.QueueComponent;
import com.example.bidwise.repository.product.CategoryRepository;
import com.example.bidwise.repository.product.ProductRepository;
import com.example.bidwise.security.UserAuthenticationService;
import com.example.bidwise.service.auction.AuctionService;
import com.example.bidwise.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AuctionService auctionService;
    private final UserAuthenticationService userAuthenticationService;
    private final QueueComponent queueComponent;
    private final BidConsumerRegistrar bidConsumerRegistrar;

    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAllByFinishedAuction(false);
    }


    @Override
    public Page<ProductEntity> findAllPagination(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findAllByFinishedAuction(false, pageable);
    }

    @Override
    public Page<ProductEntity> findAllByCategoryPagination(int page, int pageSize, Long categoryId) throws Exception {
        Pageable pageable = PageRequest.of(page, pageSize);
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(Exception::new);
        return productRepository.findAllByCategoryAndFinishedAuction(category, false, pageable);
    }

    @Override
    public ProductEntity findById(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public void addProduct(String name, String description, int price, byte[] image, Long categoryId) throws Exception {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(Exception::new);
        ProductEntity product = new ProductEntity(name, description, price, image, category);
        product.setHighestBid(price);
        product.setUserOwner(userAuthenticationService.getLoggedInUser());
        auctionService.addAuction(product, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        queueComponent.setQueueName("q.product" + product.getId());
        bidConsumerRegistrar.createBidConsumer();
    }


}
