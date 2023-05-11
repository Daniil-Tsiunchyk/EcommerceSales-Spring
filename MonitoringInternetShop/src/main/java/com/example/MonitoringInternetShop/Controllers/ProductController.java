package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Product;
import com.example.MonitoringInternetShop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String products(Model model,
                           @RequestParam(name = "category", required = false) String category,
                           @RequestParam(name = "sortBy", required = false) String sortBy) {
        List<Product> products = productService.getFilteredAndSortedProducts(category, sortBy);
        model.addAttribute("products", products);
        model.addAttribute("product", new Product());
        return "products";
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/edit")
    public String editProduct(@PathVariable("id") Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
