package poly.edu.assignment.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "client/index";
    }

    @GetMapping("/login")
    public String login() {
        return "client/login";
    }

    @GetMapping("/cart")
    public String cart() {
        return "client/cart";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/dashboard";
    }

    @GetMapping("/product/detail")
    public String productDetail() {
        return "client/product-detail";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "client/checkout";
    }

}
