package edu.poly.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

@Controller
public class paramController {

    @RequestMapping("/param/form")
    public String form() {
        return "form";
    }

    @RequestMapping("/param/save")
    public String save(@RequestParam String x, @RequestParam String y, Model model) {
        model.addAttribute("result", x + " " + y);
        model.addAttribute("x", x);
        model.addAttribute("y", y);
        return "form";
    }
}