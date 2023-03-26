package com.example.bambi.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

    @Entity
    @Table(name = "orders")
    public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "order_id")
        public Long orderId;


        @Column(name = "user_id")
        private String userId;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "total")
        private BigDecimal orderTotal;

        @Column(name = "order_completion")
        private String orderCompletion;

        @Column(name = "created_at")
        private LocalDate createdAt;

        @Column(name = "updated_at")
        private String updatedAt;


        public Order(String userId, String firstName, String lastName, BigDecimal orderTotal,
                     String orderCompletion) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.orderTotal = orderTotal;
            this.orderCompletion = orderCompletion;

        }

        public Order() {

        }

        public enum OrderStatus{
            PENDING, IN_PROGRESS, COMPLETE, CANCELLED
        }

        public Long getId() {
            return orderId;
        }

        public void setId(Long id) {
            this.orderId = orderId;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public BigDecimal getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(BigDecimal orderTotal) {
            this.orderTotal = orderTotal;
        }

        public String getOrderCompletion() {
            return orderCompletion;
        }

        public void setOrderCompletion(String orderCompletion) {
            this.orderCompletion = orderCompletion;
        }

        public LocalDate getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDate createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
