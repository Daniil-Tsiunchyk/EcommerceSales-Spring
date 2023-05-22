package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    @Autowired
    private UserRepository userRepository;

    public User getLoggedInUser(HttpSession session) {
        String login = (String) session.getAttribute("login");
        if (login != null) {
            return userRepository.findByLogin(login);
        }

        return null;
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
