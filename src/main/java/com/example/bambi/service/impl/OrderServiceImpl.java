package com.example.bambi.service.impl;

import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;
import com.example.bambi.repository.OrderRepository;
import com.example.bambi.service.OrderService;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() { return orderRepository.findAll(); }

    @Override
    public void updateOrderStatus(Long orderId, String orderCompletion) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderCompletion(orderCompletion);
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
    }

    @Override
    public List<Order> getPreviousOrdersByTimestamp(Timestamp startOfTime, Timestamp endOfTime) {
        return orderRepository.getPreviousOrdersByTimestamp(startOfTime, endOfTime);
    }

    @Override
    public List<Order> searchOrders(String search) {
        return orderRepository.findBySearch(search);
    }

}
