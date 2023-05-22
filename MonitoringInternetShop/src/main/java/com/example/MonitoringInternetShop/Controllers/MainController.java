package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Services.OrderService;
import com.example.MonitoringInternetShop.Services.ProductService;
import com.example.MonitoringInternetShop.Services.UserService;
import com.example.MonitoringInternetShop.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class MainController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        int totalUsers = userService.getAllUsers().size();
        int totalOrders = orderService.getAllOrders().size();
        int totalSales = productService.getAllProducts().stream().mapToInt(Product::getSales).sum();
        BigDecimal totalSoldAmount = productService.getAllProducts().stream()
                .map(product -> product.getPrice().multiply(new BigDecimal(product.getSales())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalSoldAmount", totalSoldAmount);

        return "main";
    }
}
