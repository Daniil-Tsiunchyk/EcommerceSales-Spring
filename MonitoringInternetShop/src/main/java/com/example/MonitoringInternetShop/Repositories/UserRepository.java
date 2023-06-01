package com.example.MonitoringInternetShop.Repositories;

import com.example.MonitoringInternetShop.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findByLogin(String login);

    Optional<User> findByMail(String mail);
}
