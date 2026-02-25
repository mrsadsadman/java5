package poly.edu.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.OrderDetail;
import poly.edu.assignment.repository.OrderDetailRepository;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public List<OrderDetail> saveAll(List<OrderDetail> details) {
        return orderDetailRepository.saveAll(details);
    }
}