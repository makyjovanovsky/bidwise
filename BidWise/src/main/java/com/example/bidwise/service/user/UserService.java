package com.example.bidwise.service.user;

import com.example.bidwise.entity.user.UserEntity;

public interface UserService {

    void registerNewUser(String firstName, String lastName, String username, String password) throws Exception;

    void addBalance(int balance);

    UserEntity findLoggedInUser();
}