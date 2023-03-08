package com.example.bambi.controller;

import com.example.bambi.entity.Product;
import com.example.bambi.entity.Size;
import com.example.bambi.service.ProductService;
import com.example.bambi.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Controller
public class ProductController {

    private final ProductService productService;
    @Autowired
    private SizeService sizeService;
    public ProductController(ProductService productService, SizeService sizeService) {
        super();
        this.productService = productService;
        this.sizeService = sizeService;
    }

    //GET request if no search all listed products shown, if search then only matched products shown
    @GetMapping("/")
    public String listAllProducts(String keyword, Model model) {
        // Default: the home page will be sorted by product name asc.

        return findPaginated(keyword,1, "productName", "asc", model);
    }
    //Handles pagination
    @GetMapping("/products/{pageNo}")
    public String findPaginated(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam(value = "sortDir") String sortDir,
                                Model model) {

        //keyword will always have a value
        if (keyword == null) {
            keyword = "";
        }

        int pageSize = 5;
        Page<Product> page = productService.findPaginated(keyword, pageNo, pageSize, sortField, sortDir);
        List<Product> listProducts = page.getContent();

        //Iterate through each product and calculate its stock level
        for(Product product : listProducts){

            //Get list of sizes
            List<Size> sizes = sizeService.getSizesByProductId(product.getId());

            //Calculate the total stock
            int totalStockLevel = 0;
            for (Size size : sizes){
                totalStockLevel += size.getProductStock();
            }

            //Calculation for stock level
            if (totalStockLevel == 0) {
                product.setStockLevel("Out of Stock");
            } else if (totalStockLevel <= 13) {
                product.setStockLevel("Low in Stock");
            } else {
                product.setStockLevel("In Stock");
            }
        }

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("keyword", keyword);
        return "products";
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


        //GET request to retrieve the Add Product Form
    @GetMapping("/products/new")
    public String addProductForm() {
        return "add_product";
    }

    //POST request to create a New Product entry
    @PostMapping("/products/new")
    public String addProductForm(@RequestParam("product_brand") String brand,
                                 @RequestParam("product_name") String name,
                                 @RequestParam("product_price") int price,
                                 @RequestParam("product_gender") String gender,
                                 @RequestParam("product_category") String category,
                                 @RequestParam("product_description") String description,
                                 @RequestParam("product_image")MultipartFile image) throws IOException {
        Product product = new Product();
        product.setProductBrand(brand);
        product.setProductName(name);
        product.setProductPrice(price);
        product.setProductGender(gender);
        product.setProductCategory(category);
        product.setProductDescription(description);

        String filename = image.getOriginalFilename();
        product.setProductImage(filename);

        productService.saveProduct(product);
        saveImageToFolder(image);
        return "redirect:/";

    }

    //GETs the existing product and produces the data in relevant form fields
    @GetMapping("/product/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "edit_product";
    }

    @PostMapping("/product/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam String productBrand,
                                @RequestParam String productName,
                                @RequestParam int productPrice,
                                @RequestParam String productGender,
                                @RequestParam String productCategory,
                                @RequestParam String productDescription,
                                @RequestParam MultipartFile productImage) throws IOException {

        //Get existing product record
        Product product = productService.getProductById(id);
        product.setId(id);
        product.setProductBrand(productBrand);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductGender(productGender);
        product.setProductCategory(productCategory);
        product.setProductDescription(productDescription);

        //If a file has been uploaded delete the old image
        if (productImage.getOriginalFilename() != "") {
            deleteImage(product.getProductImage());
            saveImageToFolder(productImage);

            //Set existing products new image
            product.setProductImage(productImage.getOriginalFilename());
        } else {
            System.out.println("No File Available");
        }

        productService.updateProduct(product);
        return "redirect:/";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        deleteImage(product.getProductImage());
        productService.deleteProductById(id);
        return "redirect:/";
    }


    //Saves Image To Folder "./bambi-photos/"
    private void saveImageToFolder(MultipartFile image) throws IOException {
        String filename = image.getOriginalFilename();

        String uploadDir = "./bambi-photos/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save uploaded file " + filename);
        }
    }

    //Param - takes image name
    //Deletes image if it exists
    private void deleteImage(String filename) {
        File image = new File("./bambi-photos/" + filename);

        if(image.exists()) {
            image.delete();
        }
    }

}