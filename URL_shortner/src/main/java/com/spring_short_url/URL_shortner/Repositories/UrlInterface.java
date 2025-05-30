package com.spring_short_url.URL_shortner.Repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.spring_short_url.URL_shortner.Models.Urls;

public interface UrlInterface extends MongoRepository<Urls, ObjectId> {
    @Query("{ 'id' : ?0 }")
    Urls findById(String id);
}