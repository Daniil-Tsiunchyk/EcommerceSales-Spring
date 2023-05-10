package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.Promotion;
import com.example.MonitoringInternetShop.Services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/promotions")
    public String promotions(Model model) {
        List<Promotion> activePromotions = promotionService.getActivePromotions();
        List<Promotion> completedPromotions = promotionService.getCompletedPromotions();

        model.addAttribute("activePromotions", activePromotions);
        model.addAttribute("completedPromotions", completedPromotions);

        return "promotions";
    }
}
