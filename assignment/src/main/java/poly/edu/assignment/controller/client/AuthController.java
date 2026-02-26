package poly.edu.assignment.controller.client;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.service.AccountService;
import poly.edu.assignment.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthService authService;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public String loginForm(Model model) {
        if (authService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("title", "Đăng nhập - ABC Shop");
        return "client/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        return accountService.findByUsername(username)
                .filter(account -> account.getPassword().equals(password))
                .filter(Account::getActivated)
                .map(account -> {
                    authService.login(account);

                    // Check for redirect after login
                    String redirectUrl = (String) session.getAttribute("redirect-uri");
                    if (redirectUrl != null) {
                        session.removeAttribute("redirect-uri");
                        return "redirect:" + redirectUrl;
                    }

                    if (authService.isAdmin()) {
                        return "redirect:/admin/category/index";
                    }
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("error",
                            "Tên đăng nhập hoặc mật khẩu không đúng, hoặc tài khoản chưa kích hoạt");
                    model.addAttribute("title", "Đăng nhập - ABC Shop");
                    return "client/login";
                });
    }

    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        return "redirect:/";
    }
}