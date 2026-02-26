package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private Double discount = 0.0;
    private Integer quantity = 0;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate = new Date();

    private Boolean available = true;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}