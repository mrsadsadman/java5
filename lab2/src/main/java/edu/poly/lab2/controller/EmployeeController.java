package edu.poly.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.lab2.model.Employee;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @RequestMapping("/list")
    public String display(Model model) {
        model.addAttribute("message", "Employee List !");
        model.addAttribute("address", "123 dien bien phu, q3, HCM");
        var employee = Employee.builder()
                .id("E001")
                .name("John")
                .build();
        model.addAttribute("employee", employee);
        return "employees/list";
    }
}