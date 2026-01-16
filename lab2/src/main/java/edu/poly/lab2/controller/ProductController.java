package edu.poly.lab2.controller;

import java.util.List;
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.poly.lab2.model.Product;

@Controller
public class ProductController {

    @GetMapping("/product/form")
    public String form(Model model) {
        Product p = new Product();
        p.setName("iPhone 30");
        p.setPrice(5000.0);

        // ?1
        model.addAttribute("product", p);

        return "product/form";
    }

    @PostMapping("/product/save")
    public String save(
            // ?2
            @ModelAttribute("product") Product p,
            Model model) {

        model.addAttribute("product", p);
        return "product/form";
    }

    // ?3
    @ModelAttribute("items")
    public List<Product> getItems() {
        return Arrays.asList(
                new Product("iphone 17", 1000.29),
                new Product("iphone 18", 1999.99));
    }
}
