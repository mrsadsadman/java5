package poly.edu.assignment.controller.client;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.service.AccountService;
import poly.edu.assignment.service.AuthService;

import java.util.Optional;

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
    public String loginForm() {
        return "client/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        Optional<Account> opt = accountService.findByUsername(username);
        if (opt.isPresent() && opt.get().getPassword().equals(password) && opt.get().getActivated()) {
            authService.login(opt.get());
            // Redirect to originally requested page if any
            String redirectUrl = (String) session.getAttribute("redirect-uri");
            if (redirectUrl != null) {
                session.removeAttribute("redirect-uri");
                return "redirect:" + redirectUrl;
            }
            return "redirect:/";
        }
        model.addAttribute("error", "Invalid username/password or account not activated");
        return "client/login";
    }

    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        return "redirect:/";
    }
}