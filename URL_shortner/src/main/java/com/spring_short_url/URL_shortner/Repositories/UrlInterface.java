package com.spring_short_url.URL_shortner.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring_short_url.URL_shortner.Models.Urls;

public interface UrlInterface extends MongoRepository<Urls, String> {
    // Custom query methods can be added here if needed
}