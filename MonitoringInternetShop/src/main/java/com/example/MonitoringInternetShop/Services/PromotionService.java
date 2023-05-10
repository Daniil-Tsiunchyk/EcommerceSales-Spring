package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.Promotion;
import com.example.MonitoringInternetShop.Repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public List<Promotion> getActivePromotions() {
        return promotionRepository.findAllByEndDateAfter(LocalDate.now());
    }

    public List<Promotion> getCompletedPromotions() {
        return promotionRepository.findAllByEndDateBefore(LocalDate.now());
    }
}
