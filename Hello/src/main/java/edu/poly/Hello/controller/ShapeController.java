package edu.poly.Hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShapeController {
    @GetMapping("/shape")
    public String showForm() {
        return "chuVi/tinhChuViDienTich";
    }

    @PostMapping("/shape/calculate")
    public String calculate(
            @RequestParam String shape,
            @RequestParam String calculation,
            @RequestParam(required = false) Double side,
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double width,
            @RequestParam(required = false) Double radius,
            @RequestParam(required = false) Double a,
            @RequestParam(required = false) Double b,
            @RequestParam(required = false) Double c,
            Model model) {

        double perimeter = 0;
        double area = 0;

        switch (shape) {
            case "square":
                perimeter = 4 * side;
                area = side * side;
                break;

            case "rectangle":
                perimeter = 2 * (length + width);
                area = length * width;
                break;

            case "circle":
                perimeter = 2 * Math.PI * radius;
                area = Math.PI * radius * radius;
                break;

            case "triangle":
                perimeter = a + b + c;
                double s = perimeter / 2;
                area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
                break;
        }

        model.addAttribute("perimeter", String.format("%.2f", perimeter));
        model.addAttribute("area", String.format("%.2f", area));
        model.addAttribute("calculation", calculation);

        return "chuVi/resuts";
    }
}
