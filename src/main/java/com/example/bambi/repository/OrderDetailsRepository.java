package com.example.bambi.repository;

import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

        List<OrderDetails> findByOrderId(Order orderId);
}
