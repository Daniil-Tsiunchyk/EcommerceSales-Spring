package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Category;
import com.example.MonitoringInternetShop.Models.OrderItem;
import com.example.MonitoringInternetShop.Models.Product;
import com.example.MonitoringInternetShop.Services.CategoryService;
import com.example.MonitoringInternetShop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String products(Model model,
                           @RequestParam(name = "category", required = false) String category,
                           @RequestParam(name = "sortBy", required = false) String sortBy) {
        List<Product> products = productService.getFilteredAndSortedProducts(category, sortBy);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());
        return "products";
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/edit")
    public String editProduct(@PathVariable Long id, Product product) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setSales(product.getSales());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setStock(product.getStock());
            productService.updateProduct(existingProduct);
        }
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        List<OrderItem> orderItems = productService.findOrderItemsByProduct(id);
        if (!orderItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("alert", "Нельзя удалить товар, пока у него есть заказы. Разберитесь с заказами перед удалением.");
            return "redirect:/products";
        }

        productService.deleteProduct(id);
        return "redirect:/products";
    }


    @PostMapping("/{id}/incrementStock")
    public String incrementProductStock(@PathVariable("id") Long id, @RequestParam("incrementAmount") Integer incrementAmount) {
        productService.incrementProductStock(id, incrementAmount);
        return "redirect:/products";
    }


    @PostMapping("/{id}/decrementStock")
    public String decrementProductStock(@PathVariable("id") Long id, @RequestParam("decrementAmount") Integer decrementAmount, Model model) {
        try {
            productService.decrementProductStock(id, decrementAmount);
        } catch (RuntimeException ex) {
            model.addAttribute("alert", ex.getMessage());
            return "products";
        }
        return "redirect:/products";
    }

    @PostMapping("/categories")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/products";
    }

}
