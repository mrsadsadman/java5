package poly.edu.assignment.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.Product;
import poly.edu.assignment.repository.ProductRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    HttpSession session;

    private Map<Integer, Integer> getCart() {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void add(Integer productId) {
        Map<Integer, Integer> cart = getCart();
        cart.put(productId, cart.getOrDefault(productId, 0) + 1);
    }

    public void remove(Integer productId) {
        getCart().remove(productId);
    }

    public void update(Integer productId, Integer quantity) {
        if (quantity <= 0)
            getCart().remove(productId);
        else
            getCart().put(productId, quantity);
    }

    public void clear() {
        session.removeAttribute("cart");
    }

    public Map<Integer, Integer> getItems() {
        return getCart();
    }

    // ⭐⭐ NEW METHOD TÍNH TOTAL
    public double getTotal(ProductRepository productRepository) {
        double total = 0.0;
        Map<Integer, Integer> cart = getItems();

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product p = productRepository.findById(entry.getKey()).orElse(null);
            if (p == null)
                continue;
            total += p.getPrice() * (1 - p.getDiscount() / 100) * entry.getValue();
        }
        return total;
    }

    public int getCount() {
        return getCart().values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean isEmpty() {
        return getCart().isEmpty();
    }
}