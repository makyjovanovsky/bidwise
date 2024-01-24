package com.example.bidwise.data;

import com.example.bidwise.entity.user.UserEntity;
import com.example.bidwise.entity.user.UserRole;
import com.example.bidwise.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializr {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeData() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(new UserEntity("Mladen", "Jovanovski", "admin", passwordEncoder.encode("admin"), UserRole.ADMIN));
        }

        if(userRepository.findByUsername("mladen").isEmpty()) {
            userRepository.save(new UserEntity("Mladen", "Jovanovski", "mladen", passwordEncoder.encode("mladen"), UserRole.USER));
        }

    }
}
