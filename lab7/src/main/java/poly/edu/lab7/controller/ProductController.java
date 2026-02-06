package poly.edu.lab7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.lab7.dao.ProductDAO;
import poly.edu.lab7.entity.Product;
import poly.edu.lab7.service.SessionService;

import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductDAO dao;

    @Autowired
    SessionService session;

    // Bài 1: Search by price range (JPQL)
    @GetMapping("/search")
    public String searchForm() {
        return "product/search";
    }

    @PostMapping("/search")
    public String search(Model model,
            @RequestParam("min") Optional<Double> min,
            @RequestParam("max") Optional<Double> max) {

        double minPrice = min.orElse(0.0);
        double maxPrice = max.orElse(Double.MAX_VALUE);

        model.addAttribute("items", dao.findByPrice(minPrice, maxPrice));
        return "product/search";
    }

    // Bài 2: Search and pagination (JPQL + Pageable)
    @GetMapping("/search-and-page")
    public String searchAndPageForm(Model model,
            @RequestParam("keywords") Optional<String> kw,
            @RequestParam("p") Optional<Integer> p) {

        return handleSearchAndPage(model, kw, p);
    }

    @PostMapping("/search-and-page")
    public String searchAndPage(Model model,
            @RequestParam("keywords") Optional<String> kw,
            @RequestParam("p") Optional<Integer> p) {

        return handleSearchAndPage(model, kw, p);
    }

    private String handleSearchAndPage(Model model, Optional<String> kw, Optional<Integer> p) {
        String keywords = kw.orElse(session.get("keywords", ""));
        session.set("keywords", keywords);

        int currentPage = p.orElse(0);
        if (currentPage < 0) {
            currentPage = 0;
        }

        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<Product> page = dao.findByKeywords("%" + keywords + "%", pageable);

        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);
        return "product/search-and-page";
    }

    // Bài 4: Search by price range (DSL)
    @GetMapping("/search-dsl")
    public String searchDSLForm() {
        return "product/search-dsl";
    }

    @PostMapping("/search-dsl")
    public String searchDSL(Model model,
            @RequestParam("min") Optional<Double> min,
            @RequestParam("max") Optional<Double> max) {

        double minPrice = min.orElse(0.0);
        double maxPrice = max.orElse(Double.MAX_VALUE);

        model.addAttribute("items", dao.findByPriceBetween(minPrice, maxPrice));
        return "product/search-dsl";
    }

    // ... các method khác ...

    // Bài 5: Search and pagination (DSL + Pageable)
    @GetMapping("/search-dsl-page")
    public String searchDSLPageForm(Model model,
            @RequestParam("keywords") Optional<String> kw,
            @RequestParam("p") Optional<Integer> p) {

        return handleSearchDSLPage(model, kw, p);
    }

    @PostMapping("/search-dsl-page")
    public String searchDSLPage(Model model,
            @RequestParam("keywords") Optional<String> kw,
            @RequestParam("p") Optional<Integer> p) {

        return handleSearchDSLPage(model, kw, p);
    }

    private String handleSearchDSLPage(Model model, Optional<String> kw, Optional<Integer> p) {
        String keywords = kw.orElse("");
        int currentPage = p.orElse(0);
        if (currentPage < 0) {
            currentPage = 0;
        }

        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<Product> page = dao.findAllByNameLike("%" + keywords + "%", pageable);

        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);
        return "product/search-dsl-page";
    }

}