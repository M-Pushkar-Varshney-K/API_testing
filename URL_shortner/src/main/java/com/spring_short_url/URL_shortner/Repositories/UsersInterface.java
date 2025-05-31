package com.spring_short_url.URL_shortner.Repositories;

// import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.spring_short_url.URL_shortner.Models.Users;

public interface UsersInterface extends MongoRepository<Users, ObjectId> {
    // Optional<Users> findByEmailAndPassword(String email, String password);
}
