package com.example.MonitoringInternetShop.Repositories;

import com.example.MonitoringInternetShop.Models.Order;
import com.example.MonitoringInternetShop.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

}
