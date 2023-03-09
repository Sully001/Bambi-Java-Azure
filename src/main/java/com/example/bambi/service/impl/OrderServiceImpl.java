package com.example.bambi.service.impl;

import com.example.bambi.entity.Order;
import com.example.bambi.repository.OrderRepository;
import com.example.bambi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() { return orderRepository.findAll(); }

}
