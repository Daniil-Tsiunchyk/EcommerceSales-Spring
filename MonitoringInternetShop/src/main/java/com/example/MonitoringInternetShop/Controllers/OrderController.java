package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Order;
import com.example.MonitoringInternetShop.Models.OrderItem;
import com.example.MonitoringInternetShop.Models.Product;
import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Services.OrderService;
import com.example.MonitoringInternetShop.Services.ProductService;
import com.example.MonitoringInternetShop.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

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
        newOrder.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for (Long productId : products) {
            Product product = productService.findById(productId);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItems.add(orderItem);
        }
        newOrder.setOrderItems(orderItems);

        orderService.saveOrder(newOrder);
        return "redirect:/orders";
    }

    @GetMapping("/createOrder")
    public String showCreateOrderPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "createOrder";
    }

    @PostMapping("/createOrder")
    public String handleCreateOrder(@RequestParam List<Long> productIds, @RequestParam List<Integer> quantities, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Product product = productService.getProductById(productId);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantities.get(i));
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        orderService.saveOrder(order);

        return "redirect:/orders";
    }
}
