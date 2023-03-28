package com.example.bambi;

import com.example.bambi.controller.ProductController;
import com.example.bambi.entity.Product;
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
class BambiApplicationTests {


    ProductService productService = mock(ProductService.class);

    ProductRepository productRepository = mock(ProductRepository.class);

    SizeService sizeService = mock(SizeService.class);

    Model model = mock(Model.class);

    ProductController controller = new ProductController(productService,sizeService);


                            /*WHILST BUILDING NEW FUNCTIONS MUST IGNORE TESTS
                              WHEN FUNCTION BUILT REBUILD TESTS TO SUIT*/

    /*------ProductService.java Tests--------*/
/*
    @Test
    public void testFindPaginatedWithKeyword() {

        // Arrange
        String keyword = "test"; int pageNo = 1; int pageSize = 10; String sortField = "name"; String sortDir = "ASC";

        // Create some dummy products
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(); product1.setProductName("Product 1");
        products.add(product1);
        Product product2 = new Product(); product2.setProductName("Product 2");
        products.add(product2);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortField).ascending());
        Page<Product> expectedPage = new PageImpl<>(products, pageable, products.size());

        Mockito.when(productRepository.findByKeyword(keyword, pageable)).thenReturn(expectedPage);

        // Act
        Page<Product> actualPage = productService.findPaginated(keyword, pageNo, pageSize, sortField, sortDir);

        // Assert
        assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testFindPaginatedWithoutKeyword() {

        // Arrange
        int pageNo = 1; int pageSize = 10; String sortField = "name"; String sortDir = "ASC";

        // Create some dummy products
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(); product1.setProductName("Product 1");
        products.add(product1);
        Product product2 = new Product(); product2.setProductName("Product 2");
        products.add(product2);

        // Act
        Sort sort = Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Product> expectedPage = Mockito.mock(Page.class);
        Mockito.when(productRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<Product> actualPage = productService.findPaginated(null, pageNo, pageSize, sortField, sortDir);

        // Assert
        assertEquals(expectedPage, actualPage);
    }
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


    /*------ProductController.java Tests--------*/

    //Test checks the method returns the correct view name and productService is called with the correct params.
    @Test
    void testListAllProducts() {
        //Arrange
        String keyword = "test";
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        PageImpl<Product> page = new PageImpl<>(productList, PageRequest.of(0, 1), 1);
        Mockito.when(productService.findPaginated(keyword, 1, 5, "productName", "asc")).thenReturn(page);

        // Act
        String viewName = controller.listAllProducts(keyword, model);

        // Assert
        assertEquals("products", viewName);
        Mockito.verify(model).addAttribute("listProducts", productList);
    }

    @Test
    public void testUpdateProductWithValidInput() throws Exception {
        // Arrange
        Long id = 1L; String productBrand = "Nike"; String productName = "Air Max"; int productPrice = 100;
        String productGender = "Male"; String productCategory = "Trainers";
        String productDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        MockMultipartFile productImage = new MockMultipartFile("product name", "image.jpg", "image/jpeg", "Some Image".getBytes());
        Product product = new Product();

        product.setProductBrand(productBrand); product.setProductName(productName);
        product.setProductPrice(productPrice); product.setProductGender(productGender);
        product.setProductCategory(productCategory); product.setProductDescription(productDescription);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Mockito.when(productService.getProductById(id)).thenReturn(product);
        BindingResult bindingResult = mock(BindingResult.class);

        // Act
        String viewName = controller.updateProduct(id, product, bindingResult, productImage, redirectAttributes);

        // Assert
        assertEquals("redirect:/products", viewName);
        Mockito.verify(productService).updateProduct(product);
        assertEquals(productBrand, product.getProductBrand());
        assertEquals(productName, product.getProductName());
        assertEquals(productPrice, product.getProductPrice());
        assertEquals(productGender, product.getProductGender());
        assertEquals(productCategory, product.getProductCategory());
        assertEquals(productDescription, product.getProductDescription());
        assertEquals(productImage.getOriginalFilename(), product.getProductImage());
    }

    //Test with invalid input empty string for productName to test error message is returned
    @Test
    public void testUpdateProductWithInvalidInputs() throws IOException {
        // Arrange
        Long id = 1L;
        Product product = new Product();
        MultipartFile productImage = mock(MultipartFile.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Mockito.when(productService.getProductById(id)).thenReturn(product);
        BindingResult bindingResult = mock(BindingResult.class);

        // Act
        String viewName = controller.updateProduct(id, product, bindingResult, productImage, redirectAttributes);

        // Assert
        assertEquals("redirect:/product/edit/" + id, viewName);
        Mockito.verify(productService, times(0)).updateProduct(product);
        assertEquals(1, redirectAttributes.getFlashAttributes().size());
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("error"));
    }

        /*Test deletes a sample product using deletingProductById(?)
    @Test
    public void testDeleteProduct() {
        // Arrange
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setProductImage(Arrays.toString(new byte[] {1, 2, 3})); // set a valid image for the product
        Mockito.when(productService.getProductById(id)).thenReturn(product);
        // Act
        String viewName = controller.deleteProduct(id);
        // Assert
        assertEquals("redirect:/", viewName);
        Mockito.verify(productService, times(1)).deleteProductById(id);
    }

*/
    //Test makes mock ProductService called the findPaginated() method, verifies the view name and model attributes are correct
    @Test
    public void testFindPaginated() {
        // Arrange
        String keyword = "test"; int pageNo = 1; String sortField = "productName"; String sortDir = "asc";

        Page<Product> page = new PageImpl<>(Arrays.asList(new Product(), new Product()));
        Mockito.when(productService.findPaginated(keyword, pageNo, 5, sortField, sortDir)).thenReturn(page);

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

    //Test that keyword can never be null
    @Test
    void testKeywordNeverNull() {
        // Arrange
        int pageNo = 1; String sortField = "productName"; String sortDir = "asc"; String keyword = "";

        Page<Product> page = new PageImpl<>(Arrays.asList(new Product(), new Product()));
        Mockito.when(productService.findPaginated(keyword, pageNo, 5, sortField, sortDir)).thenReturn(page);
        // Act
        String result = controller.findPaginated("", pageNo, sortField, sortDir, model);

        // Assert
        assertNotNull(result);
        assertNotNull(controller.findPaginated(null, pageNo, sortField, sortDir, model));
        assertNotNull(controller.findPaginated("", pageNo, sortField, sortDir, model));
    }

}
