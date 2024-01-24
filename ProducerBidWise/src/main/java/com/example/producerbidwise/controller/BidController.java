package com.example.producerbidwise.controller;

import com.example.producerbidwise.service.BidService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping("/bid")
    public String addBid(@RequestParam(name = "productId") Long productId,
                         @RequestParam(name = "userId") Long userId,
                         @RequestParam(name = "price") int price) throws Exception {
        String message = bidService.addBid(userId, productId, price);
        return "redirect:http://localhost:8080/product?message=" + message;
    }


}
