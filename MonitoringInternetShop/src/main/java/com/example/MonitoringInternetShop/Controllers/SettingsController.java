package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {
    @Autowired
    private SettingsService settingsService;

    @GetMapping("/settings")
    public String settings(Model model) {
        return "settings";
    }

    @PostMapping("/settings")
    public String updateSettings() {
        return "redirect:/settings";
    }
}
