package com.example.bambi;

import com.example.bambi.controller.OrderController;
import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;
import com.example.bambi.entity.Product;
import com.example.bambi.service.OrderDetailsService;
import com.example.bambi.service.OrderService;
import com.example.bambi.service.ProductService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderControllerTests {

    @Mock
    private  OrderService orderService;
    @Mock
    private  OrderDetailsService orderDetailsService;
    @Mock
    private  ProductService productService;
    @Mock
    private OrderController orderController;
    @InjectMocks
    private Model model = mock(Model.class);

    @Test
    public void testShowOrders() {
        // Arrange
        String keyword = "test";
        List<Order> orders = new ArrayList<>();
        List<Product> lowStockProducts = new ArrayList<>();

        when(orderService.getAllOrders()).thenReturn(orders);
        when(orderService.searchOrders(keyword)).thenReturn(orders);
        when(productService.getLowStockProducts()).thenReturn(lowStockProducts);

        // Act
        orderController = new OrderController(orderService,orderDetailsService,productService);
        String result = orderController.showOrders(model, keyword);

        // Assert
        assertEquals("orders", result);
        verify(model).addAttribute("orders", orders);
        verify(model).addAttribute("keyword", keyword);
        verify(model).addAttribute("lowStockProducts", lowStockProducts);
    }

    @Test
    public void testUpdateOrderStatus() {

        // Arrange
        Long orderId = 1L;
        String orderStatus = "test";

        // Act
        orderController = new OrderController(orderService,orderDetailsService,productService);
        String result = orderController.updateOrderStatus(orderId, orderStatus);

        // Assert
        assertEquals("redirect:/orders", result);
        verify(orderService).updateOrderStatus(orderId, orderStatus);
    }

    @Test
    public void testGetOrderDetails() {
        // Arrange
        Order orderId = new Order();
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        List<Product> lowStockProducts = new ArrayList<>();

        when(orderDetailsService.getOrderDetailsByOrderId(orderId)).thenReturn(orderDetailsList);
        when(productService.getLowStockProducts()).thenReturn(lowStockProducts);

        // Act
        orderController = new OrderController(orderService,orderDetailsService,productService);
        String result = orderController.getOrderDetails(orderId, model);

        // Assert
        assertEquals("order_details", result);
        verify(model).addAttribute("orderId", orderId);
        verify(model).addAttribute("orderDetailsList", orderDetailsList);
        verify(model).addAttribute("lowStockProducts", lowStockProducts);
    }
}
