package poly.edu.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.*;
import poly.edu.assignment.repository.OrderDetailRepository;
import poly.edu.assignment.repository.OrderRepository;
import poly.edu.assignment.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private AccountService accountService;

    public List<Order> findByAccount(Account account) {
        return orderRepository.findByAccountOrderByOrderDateDesc(account);
    }

    public Page<Order> findByAccount(Account account, Pageable pageable) {
        return orderRepository.findByAccount(account, pageable);
    }

    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrder(Account account, String address, String phone, String notes) {
        Order order = new Order();
        order.setAccount(account);
        order.setAddress(address);
        order.setPhone(phone);
        order.setNotes(notes);
        order.setStatus("Pending");

        Map<Integer, Integer> cart = cartService.getItems();
        double total = 0.0;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElse(null);
            if (product == null)
                continue;
            Integer qty = entry.getValue();
            double price = product.getPrice() * (1 - product.getDiscount() / 100);
            double subtotal = price * qty;
            total += subtotal;

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setPrice(product.getPrice());
            detail.setDiscount(product.getDiscount());
            detail.setQuantity(qty);
            detail.setTotal(subtotal);
            order.getOrderDetails().add(detail);
        }
        order.setTotalAmount(total);
        orderRepository.save(order);
        cartService.clear();
        return order;
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public void update(Order order) {
        orderRepository.save(order);
    }

    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }
}