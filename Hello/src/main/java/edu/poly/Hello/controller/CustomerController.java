package edu.poly.Hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomerController {
    @GetMapping("/customer/hello")
    @ResponseBody
    public String hello(@RequestParam("name") String name) {
        return "Hello Customer " + name;
    }

    @GetMapping("/customer/welcome/{name}")
    @ResponseBody
    public String welcome(@PathVariable("name") String name) {
        return "Welcome Customer " + name;
    }
}
