package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = userService.validateUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            return "redirect:/login";
        }
    }
}
