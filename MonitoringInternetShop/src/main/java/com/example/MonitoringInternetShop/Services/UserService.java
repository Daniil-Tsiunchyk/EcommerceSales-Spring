package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.User;
import com.example.MonitoringInternetShop.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

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
        if (user != null && user.getPassword() != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public void updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setMail(userDetails.getMail());
            user.setLogin(userDetails.getLogin());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setStatus(userDetails.getStatus());
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
