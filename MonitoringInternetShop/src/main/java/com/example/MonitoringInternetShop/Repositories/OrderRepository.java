package com.example.MonitoringInternetShop.Repositories;

import com.example.MonitoringInternetShop.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
