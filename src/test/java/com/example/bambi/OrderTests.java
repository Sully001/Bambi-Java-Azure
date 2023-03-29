package com.example.bambi;

import com.example.bambi.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderTests {

    @Test
    public void testOrder() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setUserId("user1");
        order.setFirstName("John");
        order.setLastName("Doe");
        order.setOrderTotal(new BigDecimal("100.00"));
        order.setOrderCompletion("complete");

        assertEquals(1L, (long)order.getOrderId());
        assertEquals("user1", order.getUserId());
        assertEquals("John", order.getFirstName());
        assertEquals("Doe", order.getLastName());
        assertEquals(new BigDecimal("100.00"), order.getOrderTotal());
        assertEquals("complete", order.getOrderCompletion());
    }
}

