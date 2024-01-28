package com.example.bidwise.service.user;

import com.example.bidwise.entity.product.FinishedProductEntity;
import com.example.bidwise.entity.product.ProductEntity;
import com.example.bidwise.entity.user.UserEntity;

import java.util.List;

public interface UserService {

    List<FinishedProductEntity> findAllByUserOwner();

    List<FinishedProductEntity> findAllByUserWinner();

    void registerNewUser(String firstName, String lastName, String username, String password) throws Exception;

    void addBalance(int balance);

    UserEntity findLoggedInUser();
}