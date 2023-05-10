package com.example.MonitoringInternetShop.Repositories;

import com.example.MonitoringInternetShop.Models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findAllByEndDateAfter(LocalDate date);

    List<Promotion> findAllByEndDateBefore(LocalDate date);
}
