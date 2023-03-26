package com.example.bambi.service;

import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;

import java.util.List;

public interface OrderDetailsService {
     List<OrderDetails> getOrderDetailsByOrderId(Order orderId);
}
