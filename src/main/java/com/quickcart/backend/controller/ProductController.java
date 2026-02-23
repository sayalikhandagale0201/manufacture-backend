package com.quickcart.backend.controller;

import com.quickcart.backend.model.Product;
import com.quickcart.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    // =========================
    // ADD PRODUCT
    // =========================
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam(required = false) List<MultipartFile> images,
            @RequestParam(defaultValue = "false") boolean banner,
            @RequestParam(defaultValue = "false") boolean featured,
            @RequestParam(defaultValue = "false") boolean headerSlider) throws IOException {

        Product product = productService.addProduct(
                name, description, category,
                images, banner, featured, headerSlider);

        return ResponseEntity.ok(product);
    }

    // =========================
    // GET ALL PRODUCTS
    // =========================
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // =========================
    // GET PRODUCT BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // =========================
    // UPDATE PRODUCT
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam(required = false) List<MultipartFile> images,
            @RequestParam(defaultValue = "false") boolean banner,
            @RequestParam(defaultValue = "false") boolean featured,
            @RequestParam(defaultValue = "false") boolean headerSlider) throws IOException {

        Product product = productService.updateProduct(
                id, name, description, category,
                images, banner, featured, headerSlider);

        return ResponseEntity.ok(product);
    }

    // =========================
    // DELETE PRODUCT
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted");
    }

    // =========================
    // SEARCH
    // =========================
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String q) {
        return ResponseEntity.ok(productService.searchProducts(q));
    }

    // =========================
    // HOME SECTIONS
    // =========================

    @GetMapping("/banner")
    public ResponseEntity<Product> banner() {
        return ResponseEntity.ok(productService.getBannerProduct());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Product>> featured() {
        return ResponseEntity.ok(productService.getFeaturedProducts());
    }

    @GetMapping("/header-slider")
    public ResponseEntity<List<Product>> headerSlider() {
        return ResponseEntity.ok(productService.getHeaderSliderProducts());
    }
}
