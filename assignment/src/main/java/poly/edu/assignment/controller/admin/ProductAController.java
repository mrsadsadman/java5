package poly.edu.assignment.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductAController {
    @GetMapping("/admin/products")
    public String products() {
        return "admin/product";
    }
}
