package edu.poly.lab6.service.impl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import edu.poly.lab6.entity.Category;
import edu.poly.lab6.repository.CategoryRepository;
import edu.poly.lab6.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(String id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(categoryDetails.getName());
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public long countCategories() {
        return categoryRepository.count();
    }

    @Override
    public boolean existsById(String id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

    @Override
    public List<Category> saveAllCategories(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }

    @Override
    public Page<Category> getCategoriesPaginated(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}