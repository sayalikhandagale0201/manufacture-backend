package com.quickcart.backend.service;

import com.quickcart.backend.model.Product;
import com.quickcart.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final String uploadDir = "uploads/"; // local folder path
    private final String baseUrl = "http://localhost:8080/uploads/"; // base URL for access

    // ========================
    // ADD PRODUCT
    // ========================
    public Product addProduct(
            String name,
            String description,
            String category,
            List<MultipartFile> images,
            boolean banner,
            boolean featured,
            boolean headerSlider) throws IOException {

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setBanner(banner);
        product.setFeatured(featured);
        product.setHeaderSlider(headerSlider);

        product.setImageUrls(processImages(images));

        return productRepository.save(product);
    }

    // ========================
    // UPDATE PRODUCT
    // ========================
    public Product updateProduct(
            Long id,
            String name,
            String description,
            String category,
            List<MultipartFile> images,
            boolean banner,
            boolean featured,
            boolean headerSlider) throws IOException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setBanner(banner);
        product.setFeatured(featured);
        product.setHeaderSlider(headerSlider);

        // only replace if new images uploaded
        if (images != null && !images.isEmpty()) {
            product.setImageUrls(processImages(images));
        }

        return productRepository.save(product);
    }

    // ========================
    // IMAGE PROCESSOR (SAVE & RETURN URLS)
    // ========================
    private String processImages(List<MultipartFile> images) throws IOException {

        if (images == null || images.isEmpty()) {
            return "";
        }

        List<String> urls = new ArrayList<>();

        // Ensure upload directory exists
        Files.createDirectories(Paths.get(uploadDir));

        for (MultipartFile file : images) {
            if (!file.isEmpty()) {
                // Generate unique filename to avoid collisions
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + filename);

                // Save file to local folder
                Files.write(filePath, file.getBytes());

                // Add full URL to list
                urls.add(baseUrl + filename);
            }
        }

        // Join URLs as comma-separated string
        return String.join(",", urls);
    }

    // ========================
    // DELETE
    // ========================
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // ========================
    // GET ALL
    // ========================
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ========================
    // GET BY ID
    // ========================
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // ========================
    // SEARCH
    // ========================
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    // ========================
    // DISPLAY SECTIONS
    // ========================
    public Product getBannerProduct() {
        return productRepository.findFirstByBannerTrue().orElse(null);
    }

    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }

    public List<Product> getHeaderSliderProducts() {
        return productRepository.findByHeaderSliderTrue();
    }
}
