package com.quickcart.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;

    // Images stored as comma-separated string
    @Column(length = 1000)
    private String imageUrls; // e.g., "url1,url2"

    private boolean banner;
    private boolean featured;
    private boolean headerSlider;
}
