package poly.edu.assignment.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.assignment.entity.Account;
import poly.edu.assignment.entity.Order;
import poly.edu.assignment.service.AuthService;
import poly.edu.assignment.service.OrderService;

import java.util.Optional;

@Controller
@RequestMapping("/admin/order")
public class OrderAController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AuthService authService;

    private boolean checkAdmin() {
        return authService.isAdmin();
    }

    @GetMapping("/index")
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        if (!checkAdmin())
            return "redirect:/auth/login";

        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderService.findAll(pageable);

        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("totalItems", orderPage.getTotalElements());

        return "admin/order";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        Optional<Order> order = orderService.findById(id);
        order.ifPresent(o -> model.addAttribute("order", o));
        return "admin/order-edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Order order) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        orderService.update(order);
        return "redirect:/admin/order/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        orderService.delete(id);
        return "redirect:/admin/order/index";
    }

    @GetMapping("/order/my-product-list")
    public String myOrders(Model model) {

        Account account = authService.getUser();
        if (!checkAdmin())
            return "redirect:/auth/login";

        model.addAttribute("orders",
                orderService.findByAccount(account));

        return "admin/product";
    }
}