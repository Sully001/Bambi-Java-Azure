package com.example.bambi.service;

import com.example.bambi.entity.OrderDetails;

import java.util.List;

public interface OrderDetailsService {
    public List<OrderDetails> getOrderDetailsByOrderId(Long orderId);
}
