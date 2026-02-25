package poly.edu.assignment.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.entity.Order;
import poly.edu.assignment.entity.OrderDetail;
import poly.edu.assignment.entity.Product;
import poly.edu.assignment.repository.ProductRepository;
import poly.edu.assignment.service.AuthService;
import poly.edu.assignment.service.CartService;
import poly.edu.assignment.service.OrderDetailService;
import poly.edu.assignment.service.OrderService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    // ================== CHECKOUT PAGE ==================
    @GetMapping("/checkout")
    public String checkoutForm(Model model) {

        Account user = authService.getUser();
        if (user == null)
            return "redirect:/auth/login";

        double total = cartService.getTotal(productRepository);

        model.addAttribute("user", user);
        model.addAttribute("total", total);

        return "client/checkout";
    }

    // ================== CREATE ORDER ==================
    @PostMapping("/checkout")
    public String checkout(@RequestParam("address") String address,
            @RequestParam("phone") String phone,
            @RequestParam("notes") String notes) {

        Account user = authService.getUser();
        if (user == null)
            return "redirect:/auth/login";

        Order order = orderService.createOrder(user, address, phone, notes);
        return "redirect:/order/detail/" + order.getId();
    }

    // ================== ORDER LIST ==================
    @GetMapping("/list")
    public String listOrders(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Account user = authService.getUser();
        if (user == null)
            return "redirect:/auth/login";

        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderService.findByAccount(user, pageable);

        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        return "client/order-list";
    }

    // ================== ORDER DETAIL ==================
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable("id") Integer id, Model model) {

        Account user = authService.getUser();
        if (user == null)
            return "redirect:/auth/login";

        Optional<Order> order = orderService.findById(id);

        if (order.isPresent() && order.get().getAccount().getId().equals(user.getId())) {
            model.addAttribute("order", order.get());
            model.addAttribute("details", orderDetailService.findByOrderId(id));
            return "client/order-detail";
        }
        return "redirect:/order/list";
    }
}