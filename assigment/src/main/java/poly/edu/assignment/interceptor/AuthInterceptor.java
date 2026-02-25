package poly.edu.assignment.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.service.AuthService;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthService authService;
    @Autowired
    private HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Account user = authService.getUser();
        String uri = request.getRequestURI();

        // Allow access to public pages without login
        if (uri.startsWith("/auth") || uri.startsWith("/css") || uri.startsWith("/js") ||
                uri.startsWith("/webjars") || uri.startsWith("/images") || uri.equals("/") ||
                uri.startsWith("/home") || uri.startsWith("/product") || uri.startsWith("/cart") ||
                uri.startsWith("/account/sign-up") || uri.startsWith("/account/activate") ||
                uri.startsWith("/account/forgot-password")) {
            return true;
        }

        // If not logged in, redirect to login and save current URI
        if (user == null) {
            session.setAttribute("redirect-uri", uri);
            response.sendRedirect("/auth/login");
            return false;
        }

        // Admin pages require admin role
        if (uri.startsWith("/admin") && !authService.isAdmin()) {
            response.sendRedirect("/auth/login?error=access_denied");
            return false;
        }

        return true;
    }
}