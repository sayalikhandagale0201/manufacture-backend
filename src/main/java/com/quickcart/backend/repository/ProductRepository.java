package com.quickcart.backend.repository;

import com.quickcart.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String q);

    Optional<Product> findFirstByBannerTrue();

    List<Product> findByFeaturedTrue();

    List<Product> findByHeaderSliderTrue();
}
