package com.example.bambi.service;

import com.example.bambi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Page<Product> findPaginated(String keyword, int pageNo, int pageSize, String sortField, String sortDir);

    Page<Product> findByKeyword(String keyword, Pageable pageable);


    List<Product> getAllProducts();
}