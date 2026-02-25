package poly.edu.assignment.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.edu.assignment.service.AuthService;
import poly.edu.assignment.service.ReportService;

@Controller
@RequestMapping("/admin/report")
public class ReportAController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private AuthService authService;

    private boolean checkAdmin() {
        return authService.isAdmin();
    }

    @GetMapping("/revenue")
    public String revenue(Model model) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        model.addAttribute("reports", reportService.revenueByCategory());
        return "admin/revenue";
    }

    @GetMapping("/vip")
    public String vip(Model model) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        model.addAttribute("vips", reportService.top10Vip());
        return "admin/vip";
    }
}