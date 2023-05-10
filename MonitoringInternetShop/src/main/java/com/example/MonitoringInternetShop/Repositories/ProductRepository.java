package com.example.MonitoringInternetShop.Repositories;

import com.example.MonitoringInternetShop.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
