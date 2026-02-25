package poly.edu.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.Product;
import poly.edu.assignment.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findByCategory(Integer categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    public Page<Product> search(String keyword, Pageable pageable) {
        return productRepository.search(keyword, pageable);
    }

    public List<Product> findNewest() {
        return productRepository.findTop10ByOrderByCreatedDateDesc();
    }

    public List<Product> findDiscounted() {
        return productRepository.findTop10ByDiscountGreaterThanOrderByDiscountDesc(0.0);
    }
}