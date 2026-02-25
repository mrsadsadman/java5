package poly.edu.assignment.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import poly.edu.assignment.service.ProductService;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping({ "/", "/home", "/index" })
    public String home(Model model) {
        model.addAttribute("newProducts", productService.findNewest());
        model.addAttribute("discountProducts", productService.findDiscounted());
        return "client/home";
    }
}