package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Order;
import com.example.MonitoringInternetShop.Models.Product;
import com.example.MonitoringInternetShop.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String orders(@RequestParam(required = false) String status, @RequestParam(required = false) String search, Model model) {
        List<Order> orders;
        if (status != null) {
            orders = null;
        } else if (search != null) {
            orders = orderService.searchOrders(search);
        } else {
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        return null;
    }

    @PostMapping("/orders/update/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/orders";
    }

    @PostMapping("/orders/new")
    public String createOrder(@RequestParam String user, @RequestParam List<Long> products, @RequestParam int quantity, Model model) {
        Order newOrder = new Order();
        newOrder.setUser(userService.findByName(user));
        newOrder.setProducts(productService.findProductsByIds(products));
        newOrder.setOrderDate(LocalDate.now());

        for (Product product : newOrder.getProducts()) {
            product.setQuantity(quantity);
        }

        orderService.saveOrder(newOrder);
        return "redirect:/orders";
    }

}
