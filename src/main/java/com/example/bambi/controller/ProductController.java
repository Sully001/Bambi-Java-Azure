package com.example.bambi.controller;

import com.example.bambi.Projection.ProductFrequency;
import com.example.bambi.entity.Product;
import com.example.bambi.entity.Size;
import com.example.bambi.repository.ProductRepository;
import com.example.bambi.service.ProductService;
import com.example.bambi.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductRepository productRepository;

    public ProductController(ProductService productService, SizeService sizeService) {
        super();
        this.productService = productService;
        this.sizeService = sizeService;
    }

    //GET request if no search all listed products shown, if search then only matched products shown
    @GetMapping("/products")
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
        // Get all the products from the database

        // Get all the products from the database
        List<Product> allProducts = productService.getAllProducts();
// create a list to store products that are low in stock or out of stock
        List<Product> lowStockProducts = new ArrayList<>();

//Iterate through each product and calculate its stock level based on the stock level of each size
        for(Product product : allProducts){

            //Get list of sizes
            List<Size> sizes = sizeService.getSizesByProductId(product.getId());

            //Calculate the stock level for each size and update the total stock level of the product
            int totalStockLevel = 0;
            for (Size size : sizes){
                int sizeStockLevel = size.getProductStock();
                if (sizeStockLevel == 0) {
                    // if any size is out of stock, set the product's stock level to "Out of Stock"
                    product.setStockLevel("Out of Stock");
                    // add product to lowStockProducts list
                    lowStockProducts.add(product);
                    break;
                } else if (sizeStockLevel <= 13) {
                    // if any size is low in stock, set the product's stock level to "Low in Stock"
                    product.setStockLevel("Low in Stock");
                    // add product to lowStockProducts list if not already added
                    if (!lowStockProducts.contains(product)) {
                        lowStockProducts.add(product);
                    }
                } else {
                    // if all sizes are in stock, set the product's stock level to "In Stock"
                    product.setStockLevel("In Stock");
                }
                // update the total stock level of the product
                totalStockLevel += sizeStockLevel;
            }
            if (product.getStockLevel() == null) {
                // if the product's stock level has not been set yet, set it based on the total stock level
                if (totalStockLevel == 0) {
                    product.setStockLevel("Out of Stock");
                    // add product to lowStockProducts list
                    lowStockProducts.add(product);
                } else if (totalStockLevel <= 13) {
                    product.setStockLevel("Low in Stock");
                    // add product to lowStockProducts list if not already added
                    if (!lowStockProducts.contains(product)) {
                        lowStockProducts.add(product);
                    }
                } else {
                    product.setStockLevel("In Stock");
                }
            }
        }

// check if there are any low stock products and send an alert to the admin
        if (!lowStockProducts.isEmpty()) {
            String message = "There are sizes in following products are low in stock or out of stock: ";
            for (Product p : lowStockProducts) {
                message += p.getProductName() + ", ";
            }
            // remove last comma and add a period
            message = message.substring(0, message.length() - 2) + ".";
            model.addAttribute("message", message);
        }

        // filter the products based on the current page
        List<Product> listProducts = page.getContent();

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

        //GET request to retrieve the Add Product Form
    @GetMapping("/products/new")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add_product";
    }

    //POST request to create a New Product entry
    @PostMapping("/products/new")
    public String addProductForm(@Valid Product product,
                                 BindingResult result,
                                 RedirectAttributes redirAttrs,
                                 @Valid @RequestParam(value = "product_image")MultipartFile image,
                                 @RequestParam(value = "size-4", defaultValue = "0") String size4,
                                 @RequestParam(value = "size-5", defaultValue = "0") String size5,
                                 @RequestParam(value = "size-6", defaultValue = "0") String size6,
                                 @RequestParam(value = "size-7", defaultValue = "0") String size7,
                                 @RequestParam(value = "size-8", defaultValue = "0") String size8,
                                 @RequestParam(value = "size-9", defaultValue = "0") String size9,
                                 @RequestParam(value = "size-10", defaultValue = "0") String size10,
                                 @RequestParam(value = "size-11", defaultValue = "0") String size11,
                                 @RequestParam(value = "size-12", defaultValue = "0") String size12,
                                 @RequestParam(value = "size-13", defaultValue = "0") String size13) throws IOException {
        if (result.hasErrors()) {
            return "add_product";
        }
        String filename = image.getOriginalFilename();
        product.setProductImage(filename);

        productService.saveProduct(product);

        String[] sizes = {size4, size5, size6, size7, size8, size9, size10, size11, size12, size13};

        //Loop to get all the values from input // Produces all Strings must convert to int
        for (int i = 4; i <= 13; i++) {
            Size size = new Size();
            size.setProduct(product);
            size.setProductSize(String.valueOf(i));
            size.setProductStock(Integer.valueOf(sizes[i-4]));
            sizeService.saveSize(size);
        }

        saveImageToFolder(image);
        redirAttrs.addFlashAttribute("add", "Product Added Successfully");
        return "redirect:/products";
    }

    //GETs the existing product and produces the data in relevant form fields
    @GetMapping("/product/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "edit_product";
    }

    @PostMapping("/product/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid Product product,
                                BindingResult result,
                                @Valid @RequestParam(value = "product_image") MultipartFile productImage,
                                RedirectAttributes redirectAttributes) throws IOException {

        // Check for validation errors
        if (result.hasErrors()) {
            return "edit_product";
        }

        try {
            // Get existing product record
            productService.saveProduct(product);

            //If a file has been uploaded delete the old image
            if (productImage.getOriginalFilename() != "") {
                deleteImage(product.getProductImage());
                saveImageToFolder(productImage);

                //Set existing products new image
                product.setProductImage(productImage.getOriginalFilename());
            } else {
                System.out.println("No File Available");
            }

            // Update the product
            productService.updateProduct(product);

            // Redirect to the home page
            redirectAttributes.addFlashAttribute("update", "Product Updated Successfully");
            return "redirect:/products";
        } catch (IOException e) {
            // Handle file I/O exceptions
            redirectAttributes.addFlashAttribute("error", "An error occurred while processing the product image.");
            return "redirect:/product/edit/" + id;
        } catch (Exception e) {
            // Handle all other exceptions
            redirectAttributes.addFlashAttribute("error", "An error occurred while updating the product.");
            return "redirect:/product/edit/" + id;
        }
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(id);
        //Get all sizes related to that shoe
        List<Size> sizes = sizeService.getSizesByProductId(product.getId());

        //Delete each size (each row)
        for (Size size: sizes) {
            sizeService.deleteSizeById(size.getSizeId());
        }
        deleteImage(product.getProductImage());
        productService.deleteProductById(id);
        redirectAttributes.addFlashAttribute("deleted", "Product Deleted Successfully");
        return "redirect:/products";
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