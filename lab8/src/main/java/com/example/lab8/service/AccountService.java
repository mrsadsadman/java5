package com.example.lab8.service;

import com.example.lab8.entity.Account;

public interface AccountService {
    Account findById(String username);
}