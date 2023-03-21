package com.example.bambi.service.impl;

import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;
import com.example.bambi.repository.OrderDetailsRepository;
import com.example.bambi.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }
    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(Order orderId) {
        System.out.println("Getting order details for order ID " + orderId);
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrderId(orderId);
        System.out.println("Order details: " + orderDetailsList);
        return orderDetailsList;
    }
}
