package com.example.bidwise.controller.base;

import com.example.bidwise.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@AllArgsConstructor
public class BaseController {

    private final UserService userService;

    @ModelAttribute("balance")
    public int addBalanceToModel() {
        try {
            return userService.findLoggedInUser().getBalance();
        } catch (Exception e) {
            return -1;
        }

    }
}
