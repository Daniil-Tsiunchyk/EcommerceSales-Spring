package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Promotion;
import com.example.MonitoringInternetShop.Services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

}
