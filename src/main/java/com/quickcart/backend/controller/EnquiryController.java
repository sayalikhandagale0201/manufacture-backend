package com.quickcart.backend.controller;

import com.quickcart.backend.model.Enquiry;
import com.quickcart.backend.repository.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

    @Autowired
    private EnquiryRepository enquiryRepository;

    // Submit new enquiry from contact form
    @PostMapping
    public Enquiry submitEnquiry(@RequestBody Enquiry enquiry) {
        enquiry.setDate(LocalDateTime.now()); // set submission time
        enquiry.setStatus("Pending"); // default status
        return enquiryRepository.save(enquiry);
    }

    // Admin: get all enquiri)
    @GetMapping("/admin")
    public List<Enquiry> getAllEnquiries() {
        return enquiryRepository.findAll();
    }
}