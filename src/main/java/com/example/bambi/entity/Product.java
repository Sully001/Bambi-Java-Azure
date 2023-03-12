package com.example.bambi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    public Long id;

    @NotBlank(message = "Provide a Brand Name!")
    @Column(name = "product_brand", nullable = false)
    public String productBrand;

    @NotBlank(message = "Provide a Product Name!")
    @Column(name = "product_name", nullable = false)
    public String productName;

    @Range(min = 1, max = 10000, message = "Provide a Valid Price! Between 1 - 10,000")
    @Column(name = "product_price")
    public int productPrice;

    @NotBlank(message = "Provide a Gender!")
    @Column(name = "product_gender")
    public String productGender;

    @NotBlank(message = "Provide a Category!")
    @Column(name = "product_category")
    public String productCategory;

    @NotBlank(message = "Provide a Description!")
    @Column(name = "product_description")
    public String productDescription;

    @NotBlank(message = "Provide an Image!")
    @Column(name = "product_image")
    public String productImage;

    @Transient
    private String stockLevel;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Size> sizes;

    public Product(String productBrand, String productName, int productPrice,
                   String productGender, String productCategory,
                   String productDescription, String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productGender = productGender;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productImage = productImage;
    }

    //No Args Constructor
    public Product() {}


    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductGender() {
        return productGender;
    }

    public void setProductGender(String productGender) {
        this.productGender = productGender;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }


    @Transient
    public String getLogoImagePath() {
        if (productImage == null || id == null) return null;

        return "/bambi-photos/" +  productImage;
    }

    public String getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(String stockLevel) {
        this.stockLevel = stockLevel;
    }

    public List<Size> getSizes() { return sizes; }

    public void setSizes(List<Size> sizes) { this.sizes = sizes; }
}
