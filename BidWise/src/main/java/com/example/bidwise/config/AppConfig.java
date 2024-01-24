package com.example.bidwise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DateUtils dateUtils() {
        return new DateUtils();
    }

}
