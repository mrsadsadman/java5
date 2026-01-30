package edu.poly.lab6.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Double price;

    @SuppressWarnings("deprecation")
    @Temporal(TemporalType.DATE)
    private Date createDate = new Date();
}
