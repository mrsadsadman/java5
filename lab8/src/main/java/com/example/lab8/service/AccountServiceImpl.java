package com.example.lab8.service;

import com.example.lab8.entity.Account;
import com.example.lab8.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account findById(String username) {
        return accountRepository.findById(username).orElse(null);
    }
}