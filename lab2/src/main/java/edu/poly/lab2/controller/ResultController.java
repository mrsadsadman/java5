package edu.poly.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResultController {

    @RequestMapping("/a")
    public String m1(Model model) {
        model.addAttribute("message", "I come from a");
        return "a";
    }

    @RequestMapping("/b")
    public String m2(Model model) {
        model.addAttribute("message", "I come from b");

        // ?1
        return "a";
    }

    @RequestMapping("/c")
    public String m3(RedirectAttributes params) {
        params.addAttribute("message", "I come from c");

        // ?2
        return "redirect:/a";
    }

    // ?3
    @ResponseBody
    @RequestMapping("/d")
    public String m4() {
        return "I come from d";
    }
}
