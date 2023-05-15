package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByName(String name) {
        Optional<User> userOptional = userRepository.findByName(name);
        return userOptional.orElse(null);
    }

    public User validateUser(String username, String password) {
        User user = userRepository.findByLogin(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
