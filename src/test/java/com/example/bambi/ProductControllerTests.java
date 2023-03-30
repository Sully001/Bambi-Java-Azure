package com.example.bambi;

import com.example.bambi.controller.ProductController;
import com.example.bambi.entity.Product;
import com.example.bambi.entity.Size;
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
public class ProductControllerTests {


    ProductService productService = mock(ProductService.class);

    SizeService sizeService = mock(SizeService.class);

    Model model = mock(Model.class);

    ProductController controller = new ProductController(productService,sizeService);


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

    //Test deletes a sample product using deletingProductById(?)
    @Test
    public void testDeleteProduct() {
        // Arrange
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setProductImage(Arrays.toString(new byte[] {1, 2, 3})); // set a valid image for the product
        List<Size> sizes = new ArrayList<>();
        Size size = new Size();
        size.setId(1L);
        sizes.add(size);
        Mockito.when(productService.getProductById(id)).thenReturn(product);
        Mockito.when(sizeService.getSizesByProductId(id)).thenReturn(sizes);

        // Act
        String viewName = controller.deleteProduct(id, new RedirectAttributesModelMap());

        // Assert
        assertEquals("redirect:/products", viewName);
        Mockito.verify(productService, times(1)).deleteProductById(id);
        Mockito.verify(sizeService, times(1)).deleteSizeById(1L);
    }


    //Test makes mock ProductService called the findPaginated() method, verifies the view name and model attributes are correct
    @Test
    public void testFindPaginated() {
        // Arrange
        String keyword = "test"; int pageNo = 1; int pageSize = 5;String sortField = "productName"; String sortDir = "asc";

        Page<Product> page = new PageImpl<>(Arrays.asList(new Product(), new Product()));
        Mockito.when(productService.findPaginated(keyword, pageNo, 5, sortField, sortDir)).thenReturn(page);

        // Act
        String viewName = controller.findPaginated(keyword, pageNo, pageSize, sortField, sortDir, model);
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
        int pageNo = 1; int pageSize = 5; String sortField = "productName"; String sortDir = "asc"; String keyword = "";

        Page<Product> page = new PageImpl<>(Arrays.asList(new Product(), new Product()));
        Mockito.when(productService.findPaginated(keyword, pageNo, 5, sortField, sortDir)).thenReturn(page);
        // Act
        String result = controller.findPaginated("", pageNo,pageSize, sortField, sortDir, model);

        // Assert
        assertNotNull(result);
        assertNotNull(controller.findPaginated(null, pageNo,pageSize, sortField, sortDir, model));
        assertNotNull(controller.findPaginated("", pageNo,pageSize, sortField, sortDir, model));
    }

}