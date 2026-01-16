
package edu.poly.Hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.poly.model.registerModel;

@Controller
public class ResgisterController {
    @GetMapping("/register")
    public String registerForm() {
        return "login/register";
    }

    @PostMapping("/register/save")
    public String register(Model model, registerModel registerModel) {

        String username = registerModel.getUsername();
        String password = registerModel.getPassword();
        String confirmPassword = registerModel.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "Mật khẩu xác nhận không khớp!");
            return "login/register";
        }

        // Success message
        model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("confirmPassword", confirmPassword);
        return "login/register";
    }
}