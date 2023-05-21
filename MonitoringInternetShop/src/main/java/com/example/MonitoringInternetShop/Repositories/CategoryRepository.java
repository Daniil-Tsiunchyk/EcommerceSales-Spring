package com.example.MonitoringInternetShop.Repositories;

import com.example.MonitoringInternetShop.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
