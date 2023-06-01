package com.example.MonitoringInternetShop.Controllers;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Repositories.UserRepository;
import com.example.MonitoringInternetShop.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    private static final String INVALID_USER = "Неверный логин или пароль";
    private static final String UNKNOWN_ROLE = "Неизвестная роль пользователя";
    private static final String INACTIVE_ACCOUNT = "Ваш аккаунт не активирован, обратитесь к администратору для активации";
    private static final String LOGIN_EXISTS = "Пользователь с таким логином уже существует!";
    private static final String EMAIL_EXISTS = "Пользователь с таким email уже существует!";
    private static final String PASSWORD_MISMATCH = "Пароли не совпадают!";
    private static final String REGISTRATION_SUCCESS = "Регистрация успешна. Ваш аккаунт ожидает активации.";

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Optional<User> userOptional = userService.validateUser(username, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (User.Status.ACTIVE.equals(user.getStatus())) {
                session.setAttribute("user", user);
                if (User.Role.ADMIN.equals(user.getRole())) {
                    return "redirect:/dashboard";
                } else if (User.Role.USER.equals(user.getRole())) {
                    return "redirect:/create-order";
                } else {
                    model.addAttribute("error", UNKNOWN_ROLE);
                    return "login";
                }
            } else {
                model.addAttribute("error", INACTIVE_ACCOUNT);
                return "login";
            }
        } else {
            model.addAttribute("error", INVALID_USER);
            return "login";
        }
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute User user, @RequestParam String passwordConfirm, Model model) {
        Optional<User> userFromDbOptional = userRepository.findByLogin(user.getLogin());
        Optional<User> emailFromDbOptional = userRepository.findByMail(user.getMail());

        if (userFromDbOptional.isPresent()) {
            model.addAttribute("message", LOGIN_EXISTS);
            return "registration";
        }

        if (emailFromDbOptional.isPresent()) {
            model.addAttribute("message", EMAIL_EXISTS);
            return "registration";
        }

        if (!user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("message", PASSWORD_MISMATCH);
            return "registration";
        }

        user.setStatus(User.Status.INACTIVE);
        user.setRole(User.Role.USER);
        userRepository.save(user);
        model.addAttribute("message", REGISTRATION_SUCCESS);
        return "login";
    }
}
