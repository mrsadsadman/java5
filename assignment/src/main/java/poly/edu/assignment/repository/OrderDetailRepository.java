package poly.edu.assignment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import poly.edu.assignment.dto.RevenueReportDTO;
import poly.edu.assignment.entity.OrderDetail;
import poly.edu.assignment.entity.Product;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(Integer orderId);

    @Query("SELECT DISTINCT od.product FROM OrderDetail od WHERE od.order.account.id = :accountId")
    List<Product> findProductsByAccount(@Param("accountId") Integer accountId);

    @Query("SELECT od.product, SUM(od.quantity) as totalQty FROM OrderDetail od GROUP BY od.product ORDER BY totalQty DESC")
    List<Object[]> findTopSellingProducts(Pageable pageable);

    @Query("""
                SELECT c.name,
                       SUM(od.price * od.quantity),
                       SUM(od.quantity),
                       MAX(od.price),
                       MIN(od.price),
                       AVG(od.price)
                FROM OrderDetail od
                JOIN od.product p
                JOIN p.category c
                GROUP BY c.name
            """)
    List<Object[]> revenueByCategory();

    @Query("""
                SELECT new poly.edu.assignment.dto.RevenueReportDTO(
                    c.name,
                    SUM(od.price * od.quantity),
                    SUM(od.quantity),
                    MAX(od.price),
                    MIN(od.price),
                    AVG(od.price)
                )
                FROM OrderDetail od
                JOIN od.product p
                JOIN p.category c
                GROUP BY c.name
            """)
    List<RevenueReportDTO> revenueByCategoryDTO();
}