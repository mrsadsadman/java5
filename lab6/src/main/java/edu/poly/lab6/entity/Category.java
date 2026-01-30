package edu.poly.lab6.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "name", columnDefinition = "nvarchar(50)", nullable = false)
    private String name;
}