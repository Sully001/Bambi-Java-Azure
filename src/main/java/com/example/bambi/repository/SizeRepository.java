package com.example.bambi.repository;

import com.example.bambi.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    List<Size> findByProductId(Long productId);

    @Query(value = "SELECT * FROM sizes WHERE product_stock > 0 AND product_stock <= 10", nativeQuery = true)
    List<Size> getSizesLowInStock();

    @Query(value = "SELECT * FROM sizes WHERE product_stock = 0", nativeQuery = true)
    List<Size> getSizesOutOfStock();
}
