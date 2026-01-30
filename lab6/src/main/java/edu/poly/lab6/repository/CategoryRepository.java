package edu.poly.lab6.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.poly.lab6.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
}