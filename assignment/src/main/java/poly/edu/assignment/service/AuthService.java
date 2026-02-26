package poly.edu.assignment.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.Account;

@Service
public class AuthService {
    @Autowired
    HttpSession session;

    public void login(Account account) {
        session.setAttribute("user", account);
    }

    public void logout() {
        session.removeAttribute("user");
        session.invalidate();
    }

    public Account getUser() {
        return (Account) session.getAttribute("user");
    }

    public boolean isAuthenticated() {
        return getUser() != null;
    }

    public boolean isAdmin() {
        Account user = getUser();
        return user != null && user.getRole() != null && "ADMIN".equals(user.getRole().getName());
    }
}