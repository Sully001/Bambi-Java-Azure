package com.example.bambi;
import com.example.bambi.controller.ProductController;
import com.example.bambi.controller.StockController;
import com.example.bambi.entity.Product;
import com.example.bambi.entity.Size;
import com.example.bambi.service.ProductService;
import com.example.bambi.service.SizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class StockControllerTests {

    @Mock
    private SizeService sizeService = mock(SizeService.class);

    @Mock
    private ProductService productService = mock(ProductService.class);

    @Mock
    private Model model;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testViewStock() {
        // Arrange
        long id = 1L;
        Product product = new Product();
        product.setId(id);
        List<Size> sizes = new ArrayList<>();
        List<Product> lowStockProducts = new ArrayList<>();

        when(productService.getProductById(id)).thenReturn(product);
        when(sizeService.getSizesByProductId(id)).thenReturn(sizes);
        when(productService.getLowStockProducts()).thenReturn(lowStockProducts);
        stockController = new StockController(sizeService,productService);
        // Act
        String result = stockController.viewStock(id, model);

        // Assert
        assertEquals("stock", result);
        verify(model).addAttribute("product", product);
        verify(model).addAttribute("sizes", sizes);
        verify(model).addAttribute("lowStockProducts", lowStockProducts);
    }
    @Test
    public void testUpdateProductStock() {
        // Arrange
        Long sizeId = 1L;
        int stockToAdd = 5;
        Size size = new Size();
        size.setProductStock(10);
        Product product = new Product();
        product.setId(2L);
        size.setProduct(product);
        List<Product> lowStockProducts = new ArrayList<>();

        when(sizeService.getSizeById(sizeId)).thenReturn(size);
        when(productService.getLowStockProducts()).thenReturn(lowStockProducts);

        // Act
        stockController = new StockController(sizeService,productService);
        String result = stockController.updateProductStock(sizeId, stockToAdd, model);

        // Assert
        assertEquals("redirect:/product/stock/" + product.getId(), result);
        verify(sizeService).saveSize(size);
        assertEquals(15, size.getProductStock());
        verify(model).addAttribute("lowStockProducts", lowStockProducts);
    }
}
