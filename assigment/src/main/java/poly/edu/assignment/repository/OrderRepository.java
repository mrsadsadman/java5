package poly.edu.assignment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment.entity.Account;
import poly.edu.assignment.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByAccountOrderByOrderDateDesc(Account account);

    Page<Order> findByAccount(Account account, Pageable pageable);
}