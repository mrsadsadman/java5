package poly.edu.assignment.controller.client;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.service.AccountService;
import poly.edu.assignment.service.AuthService;
import poly.edu.assignment.service.FileService;

import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthService authService;
    @Autowired
    private FileService fileService;

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("account", new Account());
        return "client/register";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("account") Account account,
            BindingResult result,
            @RequestParam("confirm") String confirm,
            Model model) {
        if (!account.getPassword().equals(confirm)) {
            result.rejectValue("password", "error.account", "Passwords do not match");
        }
        if (accountService.findByUsername(account.getUsername()).isPresent()) {
            result.rejectValue("username", "error.account", "Username already exists");
        }
        if (accountService.findByEmail(account.getEmail()).isPresent()) {
            result.rejectValue("email", "error.account", "Email already registered");
        }
        if (result.hasErrors()) {
            return "client/register";
        }
        accountService.register(account);
        model.addAttribute("message", "Registration successful! Please check your email to activate account.");
        return "client/login";
    }

    @GetMapping("/activate")
    public String activate(@RequestParam("token") String token, Model model) {
        if (accountService.activate(token)) {
            model.addAttribute("message", "Account activated. You can now log in.");
        } else {
            model.addAttribute("error", "Invalid activation token.");
        }
        return "client/login";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model) {
        Account user = authService.getUser();
        if (user == null)
            return "redirect:/auth/login";
        model.addAttribute("account", user);
        return "client/profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@Valid @ModelAttribute("account") Account account,
            BindingResult result,
            Model model) {
        Account current = authService.getUser();
        if (current == null)
            return "redirect:/auth/login";
        // Update allowed fields
        current.setFullname(account.getFullname());
        current.setPhone(account.getPhone());
        current.setAddress(account.getAddress());
        // photo handled separately if needed
        accountService.save(current);
        model.addAttribute("message", "Profile updated");
        return "client/profile";
    }

    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "client/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("old") String old,
            @RequestParam("new") String newPass,
            @RequestParam("confirm") String confirm,
            Model model) {
        Account user = authService.getUser();
        if (user == null)
            return "redirect:/auth/login";
        if (!user.getPassword().equals(old)) {
            model.addAttribute("error", "Old password incorrect");
            return "client/change-password";
        }
        if (!newPass.equals(confirm)) {
            model.addAttribute("error", "New passwords do not match");
            return "client/change-password";
        }
        accountService.changePassword(user, newPass);
        model.addAttribute("message", "Password changed successfully");
        return "client/change-password";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "client/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        if (accountService.forgotPassword(email)) {
            model.addAttribute("message", "New password has been sent to your email.");
        } else {
            model.addAttribute("error", "Email not found.");
        }
        return "client/forgot-password";
    }
}