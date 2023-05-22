package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class SettingsController {
    @Autowired
    private SettingsService settingsService;

    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        User user = settingsService.getLoggedInUser(session);
        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/settings")
    public String updateSettings(@ModelAttribute User user) {
        settingsService.updateUser(user);
        return "redirect:/settings";
    }
}
