package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String registerForm() {
        return "registration";
    }

    @PostMapping("/register")
    public String addUser(User user) {
        User userFromDb = userRepository.findByLogin(user.getLogin());

        if (userFromDb != null) {
            return "registration";
        }

        userRepository.save(user);
        return "redirect:/login";
    }
}
