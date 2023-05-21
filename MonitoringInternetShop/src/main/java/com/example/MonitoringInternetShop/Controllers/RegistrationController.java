package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute User user, @RequestParam String passwordConfirm, Model model) {
        User userFromDb = userRepository.findByLogin(user.getLogin());
        User emailFromDb = userRepository.findByMail(user.getMail());

        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует!");
            return "registration";
        }

        if (emailFromDb != null) {
            model.addAttribute("message", "Пользователь с таким email уже существует!");
            return "registration";
        }

        if (!user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("message", "Пароли не совпадают!");
            return "registration";
        }

        user.setStatus("inactive");
        user.setRole("user");
        userRepository.save(user);
        model.addAttribute("message", "Регистрация успешна. Ваш аккаунт ожидает активации.");
        return "login";
    }


}
