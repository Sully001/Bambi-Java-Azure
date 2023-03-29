package com.example.bambi;

import com.example.bambi.controller.ProductController;
import com.example.bambi.entity.Product;
import com.example.bambi.entity.Size;
import com.example.bambi.repository.ProductRepository;
import com.example.bambi.service.ProductService;
import com.example.bambi.service.SizeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;


@SpringBootTest
public class ProductTests {


    ProductService productService = mock(ProductService.class);

    ProductRepository productRepository = mock(ProductRepository.class);

    SizeService sizeService = mock(SizeService.class);

    Model model = mock(Model.class);

    ProductController controller = new ProductController(productService,sizeService);



    /*------Product.java Tests--------*/

    @Test
    void testSetAndGetStockLevel() {
        // Arrange
        Product product = new Product();
        String stockLevel = "In Stock";

        // Act
        product.setStockLevel(stockLevel);
        String retrievedStockLevel = product.getStockLevel();

        // Assert
        assertEquals(stockLevel, retrievedStockLevel);
    }
    @Test
    void testGetId() {
        // Arrange
        Long expectedId = 123L;
        Product product = new Product();
        product.setId(expectedId);

        // Act
        Long actualId = product.getId();

        // Assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void testSetId() {
        // Arrange
        Long expectedId = 123L;
        Product product = new Product();

        // Act
        product.setId(expectedId);

        // Assert
        Long actualId = product.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    void testGetProductBrand() {
        // Arrange
        String expectedString = "Test Brand";
        Product product = new Product();
        product.setProductBrand(expectedString);
        // Act
        String actualString = product.getProductBrand();

        // Assert

        assertEquals(expectedString, actualString);
    }

    @Test
    void testSetProductBrand() {
        // Arrange
        String expectedString = "Test Brand";
        Product product = new Product();

        // Act
        product.setProductBrand("Test Brand");

        // Assert
        String actualString = product.getProductBrand();
        assertEquals(expectedString, actualString);
    }

    @Test
    void testGetProductName() {
        // Arrange
        String expected = "Test Product";
        Product product = new Product();
        product.setProductName(expected);

        // Act
        String actual = product.getProductName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testSetProductName() {
        // Arrange
        String expected = "Test Product";
        Product product = new Product();

        // Act
        product.setProductName(expected);

        // Assert
        assertEquals(expected, product.getProductName());
    }

    @Test
    void testGetProductPrice() {
        // Arrange
        Product product = new Product();
        product.setProductPrice(100);

        // Act
        int result = product.getProductPrice();

        // Assert
        assertEquals(100, result);
    }

    @Test
    void testSetProductPrice() {
        // Arrange
        Product product = new Product();

        // Act
        product.setProductPrice(100);

        // Assert
        assertEquals(100, product.getProductPrice());
    }

    @Test
    public void testGetProductGender() {
        Product product = new Product();
        product.setProductGender("Male");
        assertEquals("Male", product.getProductGender());
    }

    @Test
    public void testGetProductCategory() {
        Product product = new Product();
        product.setProductCategory("Trainers");
        assertEquals("Trainers", product.getProductCategory());
    }

    @Test
    public void testGetProductDescription() {
        Product product = new Product();
        product.setProductDescription("This is a test product description.");
        assertEquals("This is a test product description.", product.getProductDescription());
    }

    @Test
    public void testSetProductGender() {
        Product product = new Product();
        product.setProductGender("Female");
        assertEquals("Female", product.getProductGender());
    }

    @Test
    public void testSetProductCategory() {
        Product product = new Product();
        product.setProductCategory("Boots");
        assertEquals("Boots", product.getProductCategory());
    }

    @Test
    public void testSetProductDescription() {
        Product product = new Product();
        product.setProductDescription("This is test product description.");
        assertEquals("This is test product description.", product.getProductDescription());
    }

}