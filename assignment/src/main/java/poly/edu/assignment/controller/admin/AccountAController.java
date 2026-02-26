package poly.edu.assignment.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.entity.Role;
import poly.edu.assignment.service.AccountService;
import poly.edu.assignment.service.AuthService;
import poly.edu.assignment.service.RoleService; // we need to create this

@Controller
@RequestMapping("/admin/account")
public class AccountAController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService; // we'll create a simple RoleService
    @Autowired
    private AuthService authService;

    private boolean checkAdmin() {
        return authService.isAdmin();
    }

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accountPage = accountService.findAll(pageable);
        model.addAttribute("accounts", accountPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", accountPage.getTotalPages());
        model.addAttribute("account", new Account());
        model.addAttribute("roles", roleService.findAll());
        return "admin/account";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        accountService.findById(id).ifPresent(a -> model.addAttribute("account", a));
        model.addAttribute("roles", roleService.findAll());
        return "admin/account-edit";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Account account) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        // Set default password? For simplicity, we'll keep as is.
        accountService.save(account);
        return "redirect:/admin/account/index";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Account account) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        accountService.save(account);
        return "redirect:/admin/account/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        accountService.deleteById(id);
        return "redirect:/admin/account/index";
    }
}