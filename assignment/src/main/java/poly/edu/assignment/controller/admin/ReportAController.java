package poly.edu.assignment.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.edu.assignment.repository.OrderDetailRepository;
import poly.edu.assignment.repository.OrderRepository;
import poly.edu.assignment.service.AuthService;
import poly.edu.assignment.service.ReportService;

@Controller

@RequestMapping("/admin/report")
public class ReportAController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private AuthService authService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;

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

    @GetMapping("/statistics")
    public String statistics(Model model) {
        if (!checkAdmin())
            return "redirect:/auth/login";

        model.addAttribute("revenueData", orderDetailRepository.revenueByCategoryDTO());
        model.addAttribute("vipCustomers", orderRepository.findTopVipCustomers(PageRequest.of(0, 10)));

        return "admin/report"; // your combined tab page
    }
}