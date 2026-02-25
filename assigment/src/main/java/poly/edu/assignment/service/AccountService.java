package poly.edu.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.entity.Role;
import poly.edu.assignment.repository.AccountRepository;
import poly.edu.assignment.repository.RoleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MailService mailService;

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    public void deleteById(Integer id) {
        accountRepository.deleteById(id);
    }

    // Registration with activation token
    public Account register(Account account) {
        account.setActivated(false);
        account.setToken(UUID.randomUUID().toString());
        Role userRole = roleRepository.findByName("USER");
        account.setRole(userRole);
        Account saved = accountRepository.save(account);
        // Send activation email
        String link = "http://localhost:8080/account/activate?token=" + account.getToken();
        mailService.send(account.getEmail(), "Activate your account",
                "Click <a href='" + link + "'>here</a> to activate.");
        return saved;
    }

    public boolean activate(String token) {
        Optional<Account> opt = accountRepository.findByToken(token);
        if (opt.isPresent()) {
            Account acc = opt.get();
            acc.setActivated(true);
            acc.setToken(null);
            accountRepository.save(acc);
            return true;
        }
        return false;
    }

    public void changePassword(Account account, String newPassword) {
        account.setPassword(newPassword); // In real app, encode password
        accountRepository.save(account);
    }

    public boolean forgotPassword(String email) {
        Optional<Account> opt = accountRepository.findByEmail(email);
        if (opt.isPresent()) {
            Account acc = opt.get();
            String newPass = UUID.randomUUID().toString().substring(0, 8);
            acc.setPassword(newPass); // encode
            accountRepository.save(acc);
            mailService.send(email, "New password", "Your new password is: " + newPass);
            return true;
        }
        return false;
    }
}