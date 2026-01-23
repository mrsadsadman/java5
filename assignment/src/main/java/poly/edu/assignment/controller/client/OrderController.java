package poly.edu.assignment.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public String orders() {
        return "client/order";
    }
}