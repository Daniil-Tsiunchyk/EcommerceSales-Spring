package com.example.MonitoringInternetShop.Repositories;

import com.example.MonitoringInternetShop.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
