package com.example.lab8.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Account {
    @Id
    private String username;
    private String password;
    private String fullname;
    private String email;
    private boolean admin; // true = admin, false = user
}