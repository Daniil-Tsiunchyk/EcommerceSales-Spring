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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (status != null && !status.isEmpty()) {
            orders = orderService.getOrdersByStatus(status);
        } else if (search != null) {
            orders = orderService.searchOrders();
        } else {
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders/update/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/orders";
    }


    @PostMapping("/orders/new")
    public String createOrder(@RequestParam String user, @RequestParam List<Long> products, @RequestParam int quantity) {
        Order newOrder = new Order();
        newOrder.setUser(userService.findByName(user).orElseThrow());
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

    @GetMapping("/create-order")
    public String showCreateOrderPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "create-order";
    }

    @PostMapping("/create-order")
    public String handleCreateOrder(@RequestParam List<Long> productIds, @RequestParam List<Integer> quantities, HttpSession session, RedirectAttributes redirectAttrs) {
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

            int quantity = quantities.get(i);
            if (product.getStock() < quantity) {
                redirectAttrs.addFlashAttribute("error", "Недостаточное количество товара на складе для товара " + product.getName());
                return "redirect:/create-order";
            }

            product.setStock(product.getStock() - quantity);
            product.setSales(product.getSales() + quantity);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setStatus("На рассмотрении");

        orderService.saveOrder(order);

        return "redirect:/user-orders";
    }

    @GetMapping("/user-orders")
    public String viewOrders(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.findOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "user-orders";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

}
