package poly.edu.assignment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poly.edu.assignment.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    Page<Product> search(@Param("keyword") String keyword, Pageable pageable);

    // For homepage: best selling, new, discount (simplified)
    List<Product> findTop10ByOrderByCreatedDateDesc(); // newest

    List<Product> findTop10ByDiscountGreaterThanOrderByDiscountDesc(Double discount); // discount
    // best selling requires join with orderdetails, we skip for brevity
}