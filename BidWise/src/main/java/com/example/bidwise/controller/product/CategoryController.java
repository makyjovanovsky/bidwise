package com.example.bidwise.controller.product;

import com.example.bidwise.controller.base.BaseController;
import com.example.bidwise.service.category.CategoryService;
import com.example.bidwise.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    public CategoryController(UserService userService, CategoryService categoryService) {
        super(userService);
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String getCategoryPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/category";
    }

    @GetMapping("/add/category")
    public String getAddCategoryPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/form";
    }

    @GetMapping("/edit/category/{id}")
    public String getEditCategoryPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("categories", categoryService.findAllExcept(id));
        model.addAttribute("category", categoryService.findById(id));
        return "category/form";
    }


    @PostMapping("/add/category")
    public String addCategory(@RequestParam(name = "name") String name, @RequestParam(name = "parent", required = false) Long parent) throws Exception {
        categoryService.addCategory(name, parent);
        return "redirect:/category";
    }

    @PostMapping("/edit/category/{id}")
    public String editCategory(@PathVariable(name = "id") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "parent", required = false) Long parent) throws Exception {
        categoryService.editCategory(id, name, parent);
        return "redirect:/category";
    }

    @PostMapping("/delete/category/{id}")
    public String deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }
}
