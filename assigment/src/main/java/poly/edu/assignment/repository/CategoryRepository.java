package poly.edu.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}