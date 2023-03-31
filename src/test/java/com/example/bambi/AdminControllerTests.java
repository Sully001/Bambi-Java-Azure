package com.example.bambi;

import com.example.bambi.controller.AdminController;
import com.example.bambi.entity.Admin;
import com.example.bambi.entity.Product;
import com.example.bambi.entity.Users;
import com.example.bambi.repository.UsersRepository;
import com.example.bambi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminControllerTests {

    @Mock
    private ProductService productService = mock(ProductService.class);
    @InjectMocks
    private Model model = mock(Model.class);

    @Test
    public void testShowRegistrationForm() {
        // Create a mock ProductService and return an empty list of low stock products
        when(productService.getLowStockProducts()).thenReturn(new ArrayList<Product>());

        AdminController adminController = new AdminController(productService);

        // Call the method to be tested
        String result = adminController.showRegistrationForm(model);

        // Verify that the method returns the expected view name
        assertEquals("register", result);

        // Verify that the low stock products and admin attributes are added to the model
        verify(model).addAttribute(eq("lowStockProducts"), anyList());
        verify(model).addAttribute(eq("admin"), any(Admin.class));

        // Verify that the ProductService's getLowStockProducts method is called exactly once
        verify(productService, times(1)).getLowStockProducts();
    }

}
