package com.example.bidwise.service.user.impl;

import com.example.bidwise.entity.product.FinishedProductEntity;
import com.example.bidwise.entity.user.UserEntity;
import com.example.bidwise.entity.user.UserRole;
import com.example.bidwise.repository.product.FinishedProductRepository;
import com.example.bidwise.repository.user.UserRepository;
import com.example.bidwise.security.UserAuthenticationService;
import com.example.bidwise.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FinishedProductRepository finishedProductRepository;
    private final UserAuthenticationService userAuthenticationService;

    @Override
    public List<FinishedProductEntity> findAllByUserOwner() {
        return finishedProductRepository.findAllByUserOwner(findLoggedInUser());
    }

    @Override
    public List<FinishedProductEntity> findAllByUserWinner() {
        List<FinishedProductEntity> products = finishedProductRepository.findAll();
        List<FinishedProductEntity> result = new ArrayList<>();

        for (FinishedProductEntity p : products) {
            if (p.getAuction().getUserWinner().getId() == findLoggedInUser().getId()) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public void registerNewUser(String firstName, String lastName, String username, String password) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("Username already exists");
        }
        userRepository.save(new UserEntity(firstName, lastName, username, passwordEncoder.encode(password), UserRole.USER));

    }

    @Override
    public void addBalance(int balance) {
        UserEntity user = userAuthenticationService.getLoggedInUser();
        user.setBalance(user.getBalance() + balance);
        userRepository.save(user);
    }

    @Override
    public UserEntity findLoggedInUser() {
        return userAuthenticationService.getLoggedInUser();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.get().getRole().toString()));

        return new User(user.get().getUsername(), user.get().getPassword(), authorities);
    }


}
