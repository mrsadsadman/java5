package poly.edu.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poly.edu.assignment.entity.OrderDetail;
import poly.edu.assignment.entity.Product;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(Integer orderId);

    // For my products: distinct products bought by account
    @Query("SELECT DISTINCT od.product FROM OrderDetail od WHERE od.order.account.id = :accountId")
    List<Product> findProductsByAccount(@Param("accountId") Integer accountId);
}