package edu.poly.lab6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.lab6.entity.Category;
import edu.poly.lab6.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("item", new Category());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "View/index";
    }

    @GetMapping("/page")
    public String page(Model model, @PageableDefault(size = 2) Pageable pageable) {
        model.addAttribute("page", categoryService.getCategoriesPaginated(pageable));
        return "/View/page";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        model.addAttribute("item", categoryService.getCategoryById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "View/index";
    }

    @PostMapping("/create")
    public String create(Category category) {
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/update")
    public String update(Category category) {
        categoryService.createCategory(category); // save = update
        return "redirect:/categories/edit/" + category.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
