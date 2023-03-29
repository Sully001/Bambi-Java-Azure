package com.example.bambi.repository;
import com.example.bambi.entity.Order;
import com.example.bambi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE created_at BETWEEN :startOfTime AND :endOfTime", nativeQuery = true)
    List<Order> getPreviousOrdersByTimestamp(@Param("startOfTime") Timestamp startOfTime, @Param("endOfTime") Timestamp endOfTime);

    @Query("SELECT orders FROM Order orders WHERE CAST(orders.orderId as string) LIKE %:keyword% " +
            "or orders.userId LIKE %:keyword% or CAST(orders.orderTotal as string) LIKE %:keyword% or " +
            "orders.orderCompletion LIKE %:keyword% or orders.firstName LIKE %:keyword%  or orders.lastName LIKE %:keyword%")
    List<Order> findBySearch(@Param("keyword") String keyword);
}
