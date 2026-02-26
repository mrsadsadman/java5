package poly.edu.assignment.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.assignment.repository.AccountRepository;
import poly.edu.assignment.repository.OrderRepository;
import poly.edu.assignment.repository.ProductRepository;

@Controller
@RequestMapping("/admin")
public class DashBoardAController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalProducts", productRepository.count());
        model.addAttribute("totalOrders", orderRepository.count());
        model.addAttribute("totalUsers", accountRepository.count());
        model.addAttribute("totalRevenue", orderRepository.sumRevenue());

        return "admin/dashboard";
    }
}