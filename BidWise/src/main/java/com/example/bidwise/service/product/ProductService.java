package com.example.bidwise.service.product;

import com.example.bidwise.entity.product.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<ProductEntity> findAll();

    Page<ProductEntity> findAllPagination(int page, int pageSize);

    Page<ProductEntity> findAllByCategoryPagination(int page, int pageSize, Long categoryId) throws Exception;

    ProductEntity findById(Long id) throws Exception;

    void addProduct(String name, String description, int price, byte[] image, Long categoryId) throws Exception;
}
