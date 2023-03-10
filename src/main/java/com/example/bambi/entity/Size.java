package com.example.bambi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sizes")

public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    public Long sizeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public Product product;

    @Column(name = "product_size")
    public String productSize;

    @Column(name = "product_stock")
    public int productStock;

    public Size(Product product, String productSize, int productStock) {
        this.product = product;
        this.productSize = productSize;
        this.productStock = productStock;
    }
    //No Args Constructor
    public Size(){}

    public Long getSizeId() {
        return sizeId;
    }

    public void setId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productId) {
        this.product = productId;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }


}
