package poly.edu.assignment.controller.client;

import java.util.List;

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
import poly.edu.assignment.service.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProductService productService;

    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        Account user = authService.getUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        if (cartService.isEmpty()) {
            return "redirect:/cart/view";
        }

        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartService.getItems());

        // Calculate total
        double total = 0.0;
        var cart = cartService.getItems();
        for (var entry : cart.entrySet()) {
            var productOpt = productService.findById(entry.getKey());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                double price = product.getPrice() * (1 - product.getDiscount() / 100);
                total += price * entry.getValue();
            }
        }
        model.addAttribute("total", total);
        model.addAttribute("title", "Thanh toán - ABC Shop");

        return "client/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            @RequestParam(value = "notes", required = false) String notes,
            Model model) {

        Account user = authService.getUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        try {
            Order order = orderService.createOrder(user, address, phone, notes);
            return "redirect:/order/detail/" + order.getId();
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cart/view";
        }
    }

    @GetMapping("/list")
    public String listOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Account user = authService.getUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderService.findByAccount(user, pageable);

        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("totalItems", orderPage.getTotalElements());
        model.addAttribute("title", "Đơn hàng của tôi - ABC Shop");

        return "client/order-list";
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable("id") Integer id, Model model) {
        Account user = authService.getUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        var orderOpt = orderService.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            // Verify that the order belongs to the current user
            if (!order.getAccount().getId().equals(user.getId()) && !authService.isAdmin()) {
                return "redirect:/order/list";
            }

            model.addAttribute("order", order);
            model.addAttribute("details", orderDetailService.findByOrderId(id));
            model.addAttribute("title", "Chi tiết đơn hàng #" + id + " - ABC Shop");

            return "client/order-detail";
        }

        return "redirect:/order/list";
    }

    @GetMapping("/my-product-list")
    public String myProducts(Model model) {
        Account user = authService.getUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        List<Product> products = orderDetailService.findProductsByAccount(user.getId());
        model.addAttribute("products", products);
        model.addAttribute("title", "Sản phẩm đã mua - ABC Shop");

        return "client/my-products";
    }
}