package poly.edu.assignment.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.Product;
import poly.edu.assignment.service.ProductService;
import poly.edu.assignment.service.CategoryService;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list-by-category/{id}")
    public String listByCategory(
            @PathVariable("id") Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "name") String sort,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Product> productPage = productService.findByCategory(categoryId, pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("category", categoryService.findById(categoryId).orElse(null));
        model.addAttribute("title", "Sản phẩm theo danh mục - ABC Shop");

        return "client/product-list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Optional<Product> productOpt = productService.findById(id);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            model.addAttribute("product", product);

            // Get related products (same category, exclude current product)
            Pageable pageable = PageRequest.of(0, 4);
            Page<Product> relatedPage = productService.findByCategory(
                    product.getCategory().getId(), pageable);

            model.addAttribute("relatedProducts",
                    relatedPage.getContent().stream()
                            .filter(p -> !p.getId().equals(id))
                            .limit(4)
                            .toList());

            model.addAttribute("title", product.getName() + " - ABC Shop");
            return "client/product-detail";
        }

        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.search(keyword, pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("title", "Tìm kiếm: " + keyword + " - ABC Shop");

        return "client/product-list";
    }

    @GetMapping("/new-arrivals")
    public String newArrivals(Model model) {
        model.addAttribute("products", productService.findNewest());
        model.addAttribute("title", "Hàng mới về - ABC Shop");
        return "client/product-list";
    }

    @GetMapping("/discounts")
    public String discounts(Model model) {
        model.addAttribute("products", productService.findDiscounted());
        model.addAttribute("title", "Sản phẩm giảm giá - ABC Shop");
        return "client/product-list";
    }
}