package com.quickcart.backend.service;

import com.quickcart.backend.model.NewsletterSubscriber;
import com.quickcart.backend.repository.NewsletterRepository;
import org.springframework.stereotype.Service;

@Service
public class NewsletterService {

    private final NewsletterRepository repository;

    public NewsletterService(NewsletterRepository repository) {
        this.repository = repository;
    }

    public String subscribe(String email) {

        if (repository.findByEmail(email).isPresent()) {
            return "Email already subscribed";
        }

        NewsletterSubscriber subscriber = new NewsletterSubscriber();
        subscriber.setEmail(email);

        repository.save(subscriber);

        return "Subscribed successfully";
    }
}
