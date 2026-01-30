package edu.poly.lab6.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.poly.lab6.entity.Category;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(String id);

    Category createCategory(Category category);

    Category updateCategory(String id, Category categoryDetails);

    void deleteCategory(String id);

    long countCategories();

    boolean existsById(String id);

    void deleteAllCategories();

    List<Category> saveAllCategories(List<Category> categories);

    Page<Category> getCategoriesPaginated(Pageable pageable);
}