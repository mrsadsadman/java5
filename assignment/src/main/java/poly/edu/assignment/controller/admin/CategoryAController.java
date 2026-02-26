package poly.edu.assignment.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.Category;
import poly.edu.assignment.service.AuthService;
import poly.edu.assignment.service.CategoryService;

import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryAController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AuthService authService;

    private boolean checkAdmin() {
        return authService.isAdmin();
    }

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryService.findAll(pageable);
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("category", new Category());
        return "admin/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        Optional<Category> category = categoryService.findById(id);
        category.ifPresent(c -> model.addAttribute("category", c));
        return "admin/category-edit";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Category category) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        categoryService.save(category);
        return "redirect:/admin/category/index";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Category category) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        categoryService.save(category);
        return "redirect:/admin/category/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        if (!checkAdmin())
            return "redirect:/auth/login";
        categoryService.deleteById(id);
        return "redirect:/admin/category/index";
    }
}