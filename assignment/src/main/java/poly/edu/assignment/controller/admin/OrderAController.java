
package poly.edu.assignment.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderAController {
    @GetMapping("/admin/orders")
    public String orders() {
        return "admin/order";
    }
}