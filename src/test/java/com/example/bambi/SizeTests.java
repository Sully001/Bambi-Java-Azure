package com.example.bambi;

import com.example.bambi.entity.Product;
import com.example.bambi.entity.Size;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SizeTests {

    @Test
    public void testSize() {
        // Arrange
        Product product = new Product();
        String productSize = "M";
        int productStock = 10;
        Size size = new Size(product, productSize, productStock);

        // Act and Assert
        assertEquals(product, size.getProduct());
        assertEquals(productSize, size.getProductSize());
        assertEquals(productStock, size.getProductStock());

        // Set new values
        Product newProduct = new Product();
        String newProductSize = "L";
        int newProductStock = 20;
        size.setProduct(newProduct);
        size.setProductSize(newProductSize);
        size.setProductStock(newProductStock);

        // Assert
        assertEquals(newProduct, size.getProduct());
        assertEquals(newProductSize, size.getProductSize());
        assertEquals(newProductStock, size.getProductStock());
    }
}