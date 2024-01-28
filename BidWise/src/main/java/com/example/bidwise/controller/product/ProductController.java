package com.example.bidwise.controller.product;

import com.example.bidwise.controller.base.BaseController;
import com.example.bidwise.security.UserAuthenticationService;
import com.example.bidwise.service.category.CategoryService;
import com.example.bidwise.service.product.ProductService;
import com.example.bidwise.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController extends BaseController {

    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UserAuthenticationService userAuthenticationService;

    public ProductController(UserService userService, ProductService productService, UserService userService1, CategoryService categoryService, UserAuthenticationService userAuthenticationService) {
        super(userService);
        this.productService = productService;
        this.userService = userService1;
        this.categoryService = categoryService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping("/product")
    public String getProductPage(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(name = "message", required = false) String message,
                                 Model model) {
        model.addAttribute("products", productService.findAllPagination(page, 3));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("userId", userAuthenticationService.getLoggedInUser().getId());
        model.addAttribute("message", message);
        return "product/product";
    }

    @GetMapping("/product/category/{id}")
    public String getProductPage(@PathVariable(name = "id") Long categoryId,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) throws Exception {
        model.addAttribute("products", productService.findAllByCategoryPagination(page, 3, categoryId));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("id", categoryId);
        model.addAttribute("userId", userAuthenticationService.getLoggedInUser().getId());
        return "product/product";
    }

    @GetMapping("/addProduct")
    public String getAddProductPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam(name = "name") String name,
                             @RequestParam(name = "description") String description,
                             @RequestParam(name = "price") int price,
                             @RequestParam(name = "image") MultipartFile multipartFile,
                             @RequestParam(name = "category") Long categoryId) throws Exception {
        productService.addProduct(name, description, price, multipartFile.getBytes(), categoryId);
        return "redirect:/product";
    }

    @GetMapping("/history")
    public String getUserProducts(Model model) {
        model.addAttribute("productsOwner", userService.findAllByUserOwner());
        model.addAttribute("productsWinner", userService.findAllByUserWinner());
        return "user/list_product";
    }


}