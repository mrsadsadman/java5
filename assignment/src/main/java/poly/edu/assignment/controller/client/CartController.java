package poly.edu.assignment.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.Product;
import poly.edu.assignment.service.CartService;
import poly.edu.assignment.service.ProductService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/view")
    public String view(Model model) {

        Map<Integer, Integer> cart = cartService.getItems();
        Map<Product, Integer> items = new HashMap<>();
        double total = 0.0;

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {

            Optional<Product> optionalProduct = productService.findById(entry.getKey());

            if (optionalProduct.isPresent()) {

                Product product = optionalProduct.get();
                items.put(product, entry.getValue());

                double price = product.getPrice() * (1 - product.getDiscount() / 100.0);
                total += price * entry.getValue();
            }
        }

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("cartCount", cartService.getCount());
        model.addAttribute("title", "Giỏ hàng - ABC Shop");

        return "client/cart";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") Integer productId) {
        cartService.add(productId);
        return "redirect:/cart/view";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer productId) {
        cartService.remove(productId);
        return "redirect:/cart/view";
    }

    @PostMapping("/update")
    public String update(@RequestParam Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().startsWith("qty_")) {
                try {
                    Integer productId = Integer.parseInt(entry.getKey().substring(4));
                    Integer quantity = Integer.parseInt(entry.getValue());
                    cartService.update(productId, quantity);
                } catch (NumberFormatException e) {
                    // Skip invalid entries
                }
            }
        }
        return "redirect:/cart/view";
    }

    @GetMapping("/clear")
    public String clear() {
        cartService.clear();
        return "redirect:/cart/view";
    }
}