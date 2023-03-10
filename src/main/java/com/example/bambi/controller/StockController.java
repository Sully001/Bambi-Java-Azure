package com.example.bambi.controller;

import com.example.bambi.entity.Product;
import com.example.bambi.entity.Size;
import com.example.bambi.service.ProductService;
import com.example.bambi.service.SizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StockController {

    private final SizeService sizeService;
    private final ProductService productService;

    public StockController(SizeService sizeService, ProductService productService) {
        this.sizeService = sizeService;
        this.productService = productService;
    }

    //GET request to retrieve Stock info with all sizes
    @GetMapping("/product/stock/{id}")
    public String viewStock(@PathVariable(value = "id") long id, Model model) {

        // Get the product details by ID
        Product product = productService.getProductById(id);

        //Get list of sizes
        List<Size> sizes = sizeService.getSizesByProductId(product.getId());

        //Calculate the total stock
        int totalStockLevel = 0;
        for (Size size : sizes){
            totalStockLevel += size.getProductStock();
        }

        //Add the stock details to the model
        model.addAttribute("product", product);
        model.addAttribute("sizes", sizes);
        model.addAttribute("totalStockLevel", totalStockLevel);

        return "stock";
    }

    @PostMapping("product/stock/{sizeId}")
    public String updateProductStock(@PathVariable Long sizeId, @RequestParam("stockToAdd") int stockToAdd) {
        Size size = sizeService.getSizeById(sizeId);

        int newStockLevel = size.getProductStock() + stockToAdd;
        size.setProductStock(newStockLevel);
        sizeService.saveSize(size);

        return "redirect:/product/stock/" + size.getProduct().getId();

    }


}
