package poly.edu.assignment.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.assignment.entity.Product;
import poly.edu.assignment.service.ProductService;

@Controller
@RequestMapping("/admin/product")
public class ProductAController {

    @Autowired
    private ProductService productService;

    // ✅ LIST + PAGINATION
    @GetMapping("")
    public String list(Model model,
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId) {

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("page", productService.search(keyword, pageable));
            model.addAttribute("keyword", keyword);
        } else if (categoryId != null) {
            model.addAttribute("page", productService.findByCategory(categoryId, pageable));
            model.addAttribute("categoryId", categoryId);
        } else {
            model.addAttribute("page", productService.findAll(pageable));
        }

        return "admin/product/list";
    }

    // ✅ SHOW CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/form";
    }

    // ✅ EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("product",
                productService.findById(id).orElse(new Product()));
        return "admin/product/form";
    }

    // ✅ SAVE (CREATE + UPDATE)
    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin/product";
    }

    // ✅ DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/admin/product";
    }
}