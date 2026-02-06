package poly.edu.lab7.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.edu.lab7.dao.ProductDAO;
import poly.edu.lab7.entity.Report;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ProductDAO dao;

    @GetMapping("/inventory-by-category")
    public String inventory(Model model) {
        try {
            List<Report> items = dao.getInventoryByCategory();
            model.addAttribute("items", items);
            System.out.println("Report items count: " + (items != null ? items.size() : 0));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("items", Collections.emptyList());
        }
        return "report/inventory-by-category";
    }
}