package com.example.bambi.service.impl;

import com.example.bambi.entity.Product;
import com.example.bambi.repository.ProductRepository;
import com.example.bambi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findPaginated(String keyword, int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findByKeyword(keyword, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Product> findByKeyword(String keyword, Pageable pageable) {  return productRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public List<Product> getAllProducts() { return productRepository.findAll(); }

}