package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.Promotion;
import com.example.MonitoringInternetShop.Repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public void addPromotion(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    public Promotion getPromotionById(Long id) {
        Optional<Promotion> promotion = promotionRepository.findById(id);
        return promotion.orElse(null);
    }

    public void updatePromotion(Long id, Promotion updatedPromotion) {
        Optional<Promotion> existingPromotionOpt = promotionRepository.findById(id);
        if (existingPromotionOpt.isPresent()) {
            Promotion existingPromotion = existingPromotionOpt.get();
            if (updatedPromotion.getName() != null)
                existingPromotion.setName(updatedPromotion.getName());
            if (updatedPromotion.getStartDate() != null)
                existingPromotion.setStartDate(updatedPromotion.getStartDate());
            if (updatedPromotion.getEndDate() != null)
                existingPromotion.setEndDate(updatedPromotion.getEndDate());
            if (updatedPromotion.getConditions() != null)
                existingPromotion.setConditions(updatedPromotion.getConditions());
            promotionRepository.save(existingPromotion);
        }
    }

    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }
}
