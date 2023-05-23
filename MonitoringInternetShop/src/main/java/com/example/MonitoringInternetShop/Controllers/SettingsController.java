package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SettingsController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/settings")
    public String settingsForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "settings";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/settings")
    public String updateUser(@ModelAttribute User updatedUser, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser != null) {
            currentUser.setName(updatedUser.getName());
            currentUser.setMail(updatedUser.getMail());
            currentUser.setLogin(updatedUser.getLogin());
            userRepository.save(currentUser);
            session.setAttribute("user", currentUser);
        }
        return "redirect:/settings";
    }
}
