package com.example.bidwise.controller.base;

import com.example.bidwise.controller.base.BaseController;
import com.example.bidwise.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController extends BaseController {

    public PageController(UserService userService) {
        super(userService);
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

}
