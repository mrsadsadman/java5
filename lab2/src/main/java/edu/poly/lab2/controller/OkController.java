package edu.poly.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller

public class OkController {
    @RequestMapping("/ok")
    public String ok(Model model, HttpServletRequest request) {

        String url = request.getRequestURL().toString();
        String query = request.getQueryString();
        String method = request.getMethod();

        String fullUrl = method + " " + url + (query != null ? "?" + query : "");

        model.addAttribute("result", fullUrl);
        return "ok";
    }

    // public String m1(Model model, HttpServletRequest request) {
    // model.addAttribute("result", request.getServletPath());

    // return "ok";
    // }

    // public String m2(Model model, HttpServletRequest request) {
    // model.addAttribute("result", request.getServletPath());

    // return "ok";
    // }

    // public String m3(Model model, HttpServletRequest request) {
    // model.addAttribute("result", request.getServletPath());

    // return "ok";
    // }

}