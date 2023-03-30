package com.example.bambi.repository;

import com.example.bambi.Projection.ProductFrequency;
import com.example.bambi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    //Query matching name, brand, price, gender and category against SQL db
    @Query("SELECT products FROM Product products WHERE products.productName LIKE %:keyword% " +
            "or products.productBrand LIKE %:keyword% or CAST(products.productPrice as string) LIKE %:keyword% or " +
            "products.productGender LIKE %:keyword% or products.productCategory LIKE %:keyword%")
    Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    @Query(value = "select product_id, COUNT(product_id) " +
            "AS FREQUENCY from order_details GROUP BY product_id " +
            "ORDER BY COUNT(product_id) DESC LIMIT 3;", nativeQuery = true)
    public List<ProductFrequency> findProductFrequency();

}