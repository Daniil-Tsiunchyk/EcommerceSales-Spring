package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Promotion;
import com.example.MonitoringInternetShop.Services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/promotions")
    public String showPromotionsPage(Model model) {
        model.addAttribute("activePromotions", promotionService.getActivePromotions());
        model.addAttribute("completedPromotions", promotionService.getCompletedPromotions());
        return "promotions";
    }

    @PostMapping("/promotions/add")
    public String addPromotion(@ModelAttribute Promotion promotion) {
        promotionService.addPromotion(promotion);
        return "redirect:/promotions";
    }

    @GetMapping("/promotions/edit/{id}")
    public String editPromotion(@PathVariable("id") Long id, Model model) {
        Promotion promotion = promotionService.getPromotionById(id);
        if (promotion != null) {
            model.addAttribute("promotion", promotion);
            return "editPromotion";
        } else {
            return "redirect:/promotions";
        }
    }

    @PostMapping("/promotions/edit/{id}")
    public String updatePromotion(@PathVariable("id") Long id, @ModelAttribute Promotion promotion) {
        promotionService.updatePromotion(id, promotion);
        return "redirect:/promotions";
    }

    @GetMapping("/promotions/delete/{id}")
    public String deletePromotion(@PathVariable("id") Long id) {
        promotionService.deletePromotion(id);
        return "redirect:/promotions";
    }
}
