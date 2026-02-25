package poly.edu.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.assignment.repository.OrderDetailRepository;
import poly.edu.assignment.repository.OrderRepository;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;

    // Revenue by category: returns list of Object[] with fields: categoryName,
    // totalRevenue, totalQuantity, maxPrice, minPrice, avgPrice
    public List<Object[]> revenueByCategory() {
        // We'll write a native query or JPQL; for brevity, we'll assume a custom query
        // in repository.
        // For now, we'll return an empty list.
        return List.of();
    }

    // Top 10 VIP customers: returns list of Object[] with: fullname, totalSpent,
    // firstOrderDate, lastOrderDate
    public List<Object[]> top10Vip() {
        return List.of();
    }
}