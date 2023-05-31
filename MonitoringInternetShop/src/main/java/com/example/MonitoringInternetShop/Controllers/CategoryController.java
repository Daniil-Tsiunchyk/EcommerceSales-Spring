package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Category;
import com.example.MonitoringInternetShop.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/products";
    }

}
