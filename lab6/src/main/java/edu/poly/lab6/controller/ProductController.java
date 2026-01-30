package edu.poly.lab6.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.poly.lab6.entity.Product;
import edu.poly.lab6.dao.ProductDAO;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductDAO dao;

    // ================= CRUD =================

    @RequestMapping("/index")
    public String index(Model model) {
        Product item = new Product();
        model.addAttribute("item", item);
        model.addAttribute("items", dao.findAll());
        return "product/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("item", dao.findById(id).get());
        model.addAttribute("items", dao.findAll());
        return "product/index";
    }

    @RequestMapping("/create")
    public String create(Product item) {
        dao.save(item);
        return "redirect:/product/index";
    }

    @RequestMapping("/update")
    public String update(Product item) {
        dao.save(item);
        return "redirect:/product/edit/" + item.getId();
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        dao.deleteById(id);
        return "redirect:/product/index";
    }

    // ================= SORT =================

    @RequestMapping("/sort")
    public String sort(Model model,
            @RequestParam("field") Optional<String> field) {

        Sort sort = Sort.by(
                Sort.Direction.DESC,
                field.orElse("price"));

        List<Product> items = dao.findAll(sort);

        model.addAttribute("items", items);
        model.addAttribute("field", field.orElse("price").toUpperCase());

        return "product/sort";
    }

    // ================= PAGINATION =================

    @RequestMapping("/page")
    public String paginate(Model model,
            @RequestParam("p") Optional<Integer> p) {

        Pageable pageable = PageRequest.of(p.orElse(0), 3);
        Page<Product> page = dao.findAll(pageable);

        model.addAttribute("page", page);
        return "product/page";
    }
}
