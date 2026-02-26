package poly.edu.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import poly.edu.assignment.dto.RevenueReportDTO;
import poly.edu.assignment.entity.OrderDetail;
import poly.edu.assignment.entity.Product;
import poly.edu.assignment.repository.OrderDetailRepository;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public List<Product> findProductsByAccount(Integer accountId) {
        return orderDetailRepository.findProductsByAccount(accountId);
    }

    public List<Object[]> findTopSellingProducts(Pageable pageable) {
        return orderDetailRepository.findTopSellingProducts(pageable);
    }

    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetail> saveAll(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAll(orderDetails);
    }

    public List<Object[]> revenueByCategory() {
        return orderDetailRepository.revenueByCategory();
    }

    public List<RevenueReportDTO> revenueByCategoryDTO() {
        return orderDetailRepository.revenueByCategoryDTO();
    }
}