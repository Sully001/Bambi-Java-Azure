package com.example.bambi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    public Order orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public Product product;

    @Column(name = "product_size")
    public String size;

    @Column(name = "quantity")
    public int quantity;

    public Long getId() { return id;}

    public void setId(Long id) { this.id = id;}

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Order getOrder() {
        return orderId;
    }

    public void setOrder(Order orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

