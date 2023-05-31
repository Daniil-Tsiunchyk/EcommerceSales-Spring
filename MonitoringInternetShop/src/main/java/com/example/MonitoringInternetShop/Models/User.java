package com.example.MonitoringInternetShop.Models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String mail;
    private String login;
    private String password;
    private String role;
    private String status;
}
