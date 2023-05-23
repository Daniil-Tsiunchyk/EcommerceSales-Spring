package com.example.MonitoringInternetShop.Repositories;

import com.example.MonitoringInternetShop.Models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByProduct_Id(Long productId);
}
