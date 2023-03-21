package com.example.bambi.repository;

import com.example.bambi.Projection.ProductFrequency;
import com.example.bambi.entity.Order;
import com.example.bambi.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE created_at BETWEEN :startOfTime AND :endOfTime", nativeQuery = true)
    public List<Order> getPreviousOrdersByTimestamp(@Param("startOfTime") Timestamp startOfTime, @Param("endOfTime") Timestamp endOfTime);

    @Query(value = "SELECT SUM(total) FROM orders WHERE created_at BETWEEN :startOfTime AND :endOfTime", nativeQuery = true)
    public int getRevenueBetweenTimestamp(@Param("startOfTime") Timestamp startOfTime, @Param("endOfTime") Timestamp endOfTime);

    List<OrderDetails> findByOrderId(Long orderId);
}
