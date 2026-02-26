package poly.edu.assignment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.edu.assignment.dto.VipCustomerDTO;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByAccountOrderByOrderDateDesc(Account account);

    Page<Order> findByAccount(Account account, Pageable pageable);

    @Query("""
                SELECT o.account.fullname,
                       SUM(o.totalAmount),
                       MIN(o.orderDate),
                       MAX(o.orderDate)
                FROM Order o
                GROUP BY o.account.fullname
                ORDER BY SUM(o.totalAmount) DESC
            """)
    List<Object[]> top10Vip(Pageable pageable);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
    Double sumRevenue();

    @Query("""
                SELECT new poly.edu.assignment.dto.VipCustomerDTO(
                    o.account.fullname,
                    SUM(o.totalAmount),
                    MIN(o.orderDate),
                    MAX(o.orderDate)
                )
                FROM Order o
                GROUP BY o.account.fullname
                ORDER BY SUM(o.totalAmount) DESC
            """)
    List<VipCustomerDTO> findTopVipCustomers(Pageable pageable);
}