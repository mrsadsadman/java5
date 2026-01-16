package edu.poly.Hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
    @Autowired
    HttpServletRequest request;

    @GetMapping("/login/form")
    public String form() {
        return "login/form";
    }

    @PostMapping("/login/check")
    public String login(
            @RequestParam String username,
            @RequestParam String password, Model model) {

        if ("poly".equals(username) && "123".equals(password)) {
            model.addAttribute("message", "Hello, " + username + " !");
            model.addAttribute("title", "Greeting Page");
            model.addAttribute("welcome", "Welcome to the Spring MVC Hello World Example");
            return "hello/hello";
        } else {
            model.addAttribute("message", "sai tai khoan hoac mat khau");
            return "login/form";
        }
    }

}
