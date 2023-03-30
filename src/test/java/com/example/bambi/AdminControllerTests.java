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
    private AdminController adminController;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private ProductService productService;
    @InjectMocks
    private Model model = mock(Model.class);

    @Test
    public void testShowRegistrationForm() {

        // Act
        adminController = new AdminController();
        String result = adminController.showRegistrationForm(model);

        // Assert
        assertEquals("register", result);
        verify(model).addAttribute(eq("admin"), any(Admin.class));
}
}
