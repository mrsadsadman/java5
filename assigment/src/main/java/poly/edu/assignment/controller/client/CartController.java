package poly.edu.assignment.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.assignment.entity.Product;
import poly.edu.assignment.service.CartService;
import poly.edu.assignment.service.ProductService;

import java.util.HashMap;
import java.util.Map;

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
        double total = 0;

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {

            Product p = productService.findById(entry.getKey()).orElse(null);

            if (p != null) {
                items.put(p, entry.getValue());

                double price = p.getPrice() == null ? 0 : p.getPrice();
                double discount = p.getDiscount() == null ? 0 : p.getDiscount();

                total += price * (1 - discount / 100.0) * entry.getValue();
            }
        }

        model.addAttribute("items", items);
        model.addAttribute("total", total);

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
        for (String key : params.keySet()) {
            if (key.startsWith("qty_")) {
                Integer productId = Integer.parseInt(key.substring(4));
                Integer qty = Integer.parseInt(params.get(key));
                cartService.update(productId, qty);
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