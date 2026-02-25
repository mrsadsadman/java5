package poly.edu.assignment.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.assignment.entity.Category;
import poly.edu.assignment.entity.Product;
import poly.edu.assignment.service.CategoryService;
import poly.edu.assignment.service.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list-by-category/{id}")
    public String listByCategory(@PathVariable("id") Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.findByCategory(categoryId, pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        Optional<Category> category = categoryService.findById(categoryId);
        category.ifPresent(c -> model.addAttribute("category", c));
        return "client/product-list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            // Related products (same category)
            List<Product> related = productService.findByCategory(
                    product.get().getCategory().getId(),
                    PageRequest.of(0, 4)).getContent();
            model.addAttribute("relatedProducts", related);
            return "client/product-detail";
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.search(keyword, pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "client/product-list";
    }
}