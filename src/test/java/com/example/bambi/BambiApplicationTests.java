package com.example.bambi;

import com.example.bambi.controller.ProductController;
import com.example.bambi.entity.Product;
import com.example.bambi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class BambiApplicationTests {

    /*------ProductController.java Tests--------*/

    //Test checks the method returns the correct view name and productService is called with the correct params.
    @Test
    void testListAllProducts() {
        //Arrange
        String keyword = "test";
        Model model = Mockito.mock(Model.class);
        ProductService productService = Mockito.mock(ProductService.class);
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        PageImpl<Product> page = new PageImpl<>(productList, PageRequest.of(0, 1), 1);
        Mockito.when(productService.findPaginated(keyword, 1, 5, "productName", "asc")).thenReturn(page);
        ProductController controller = new ProductController(productService);
        // Act
        String viewName = controller.listAllProducts(keyword, model);
        // Assert
        assertEquals("products", viewName);
        Mockito.verify(model).addAttribute("listProducts", productList);
    }

    //Test update a product with valid inputs
    @Test
    public void testUpdateProductWithValidInput() throws Exception {
        // Arrange
        Long id = 1L;
        String productBrand = "Nike";
        String productName = "Air Max";
        int productPrice = 100;
        String productGender = "Male";
        String productCategory = "Trainers";
        String productDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        MockMultipartFile productImage = new MockMultipartFile("productImage", "image.jpg", "image/jpeg", "Some Image".getBytes());

        ProductService productService = Mockito.mock(ProductService.class);
        Product product = new Product();
        Mockito.when(productService.getProductById(id)).thenReturn(product);
        ProductController controller = new ProductController(productService);

        // Act
        String viewName = controller.updateProduct(id, productBrand, productName, productPrice, productGender, productCategory, productDescription, productImage);

        // Assert
        assertEquals("redirect:/", viewName);
        Mockito.verify(productService).updateProduct(product);
        assertEquals(productBrand, product.getProductBrand());
        assertEquals(productName, product.getProductName());
        assertEquals(productPrice, product.getProductPrice());
        assertEquals(productGender, product.getProductGender());
        assertEquals(productCategory, product.getProductCategory());
        assertEquals(productDescription, product.getProductDescription());
        assertEquals(productImage.getOriginalFilename(), product.getProductImage());
    }

    //Test with invalid input empty string for productName to test Exception is called
    @Test
    public void testUpdateProductWithInvalidInputs() throws IOException {
        // Arrange
        Long id = 1L;
        String productBrand = "";
        String productName = "Product Name";
        int productPrice = 0;
        String productGender = "Male";
        String productCategory = "Boots";
        String productDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        MultipartFile productImage = Mockito.mock(MultipartFile.class);
        Model model = Mockito.mock(Model.class);
        ProductService productService = Mockito.mock(ProductService.class);
        Product product = new Product();
        Mockito.when(productService.getProductById(id)).thenReturn(product);
        ProductController controller = new ProductController(productService);
        // Act & Assert
        assertThrows(Exception.class, () -> controller.updateProduct(id, productBrand, productName, productPrice, productGender,
                productCategory, productDescription, productImage));
        Mockito.verify(productService, Mockito.times(0)).updateProduct(product);
    }

    //Test deletes a sample product using deletingProductById(?)
    @Test
    public void testDeleteProduct() {
        // Arrange
        Long id = 1L;
        ProductService productService = Mockito.mock(ProductService.class);
        ProductController controller = new ProductController(productService);
        Product product = new Product();
        product.setId(id);
        product.setProductImage(Arrays.toString(new byte[] {1, 2, 3})); // set a valid image for the product
        Mockito.when(productService.getProductById(id)).thenReturn(product);
        // Act
        String viewName = controller.deleteProduct(id);
        // Assert
        assertEquals("redirect:/", viewName);
        Mockito.verify(productService, Mockito.times(1)).deleteProductById(id);
    }


    //Test makes mock ProductService called the findPaginated() method, verifies the view name and model attributes are correct
    @Test
    public void testFindPaginated() {
        // Arrange
        String keyword = "test";
        int pageNo = 1;
        String sortField = "productName";
        String sortDir = "asc";
        Model model = Mockito.mock(Model.class);
        ProductService productService = Mockito.mock(ProductService.class);
        Page<Product> page = new PageImpl<>(Arrays.asList(new Product(), new Product()));
        Mockito.when(productService.findPaginated(keyword, pageNo, 5, sortField, sortDir)).thenReturn(page);
        ProductController controller = new ProductController(productService);
        // Act
        String viewName = controller.findPaginated(keyword, pageNo, sortField, sortDir, model);
        // Assert
        assertEquals("products", viewName);
        Mockito.verify(model).addAttribute("currentPage", pageNo);
        Mockito.verify(model).addAttribute("totalPages", page.getTotalPages());
        Mockito.verify(model).addAttribute("totalItems", page.getTotalElements());
        Mockito.verify(model).addAttribute("sortField", sortField);
        Mockito.verify(model).addAttribute("sortDir", sortDir);
        Mockito.verify(model).addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        Mockito.verify(model).addAttribute("listProducts", page.getContent());
        Mockito.verify(model).addAttribute("keyword", keyword);
    }

}
