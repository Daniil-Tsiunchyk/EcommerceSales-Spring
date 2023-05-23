package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.Order;
import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Repositories.OrderRepository;
import com.example.MonitoringInternetShop.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Order> getLatestOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderDate")).stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> searchOrders(String keyword) {
        return null;
    }

    public void updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Autowired
    public OrderService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public List<Order> findOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}

