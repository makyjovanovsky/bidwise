package com.example.bidwise.controller.user;

import com.example.bidwise.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class BalanceController {

    private final UserService userService;

    @GetMapping("/addBalance")
    public String getAddBalancePage() {
        return "payment/payment";
    }

    @PostMapping("/addBalance")
    public String addBalance(@RequestParam(name = "balance") int balance) {
        System.out.println(balance);
        userService.addBalance(balance);
        return "payment/payment_successful";
    }
}
