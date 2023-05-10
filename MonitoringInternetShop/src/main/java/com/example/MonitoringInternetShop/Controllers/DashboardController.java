package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Order;
import com.example.MonitoringInternetShop.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Product> topProducts = productService.getTopProducts();
        List<Order> latestOrders = orderService.getLatestOrders();

        model.addAttribute("topProducts", topProducts);
        model.addAttribute("latestOrders", latestOrders);

        int totalSoldProducts = latestOrders.stream()
                .mapToInt(order -> order.getProducts().size())
                .sum();

        BigDecimal totalRevenue = latestOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageCheck = latestOrders.size() > 0
                ? totalRevenue.divide(BigDecimal.valueOf(latestOrders.size()), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        model.addAttribute("totalSoldProducts", totalSoldProducts);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("averageCheck", averageCheck);

        return "dashboard";
    }

}
