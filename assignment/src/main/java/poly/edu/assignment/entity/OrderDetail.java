package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "OrderDetails") // ✅ đúng tên bảng SQL Server
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double discount = 0.0;

    @Column(nullable = false)
    private Double total;
}