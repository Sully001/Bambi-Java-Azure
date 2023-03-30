package com.example.bambi;

import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;
import com.example.bambi.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderDetailsTests {
    @Test
    public void testOrderDetails() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(1L);
        orderDetails.setOrder(new Order());
        orderDetails.setProduct(new Product());
        orderDetails.setSize("large");
        orderDetails.setQuantity(2);

        assertEquals(1L, (long)orderDetails.getId());
        assertNotNull(orderDetails.getOrder());
        assertNotNull(orderDetails.getProduct());
        assertEquals("large", orderDetails.getSize());
        assertEquals(2, orderDetails.getQuantity());
    }
}
