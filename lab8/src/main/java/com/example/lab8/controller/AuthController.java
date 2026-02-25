package com.example.lab8.controller;

import com.example.lab8.entity.Account;
import com.example.lab8.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginProcess(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        Account user = accountService.findById(username);
        if (user == null) {
            model.addAttribute("message", "Tài khoản không tồn tại!");
        } else if (!user.getPassword().equals(password)) {
            model.addAttribute("message", "Mật khẩu không đúng!");
        } else {
            session.setAttribute("user", user);
            model.addAttribute("message", "Đăng nhập thành công!");

            // Chuyển hướng về URI bảo mật nếu có (sẽ dùng ở bài 5)
            String securityUri = (String) session.getAttribute("securityUri");
            if (securityUri != null) {
                session.removeAttribute("securityUri");
                return "redirect:" + securityUri;
            }
        }
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("user");
        return "redirect:/auth/login";
    }
}