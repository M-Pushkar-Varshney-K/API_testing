package com.spring_short_url.URL_shortner.Models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class Users {
    @Id
    private ObjectId id;
    private String name;
    @Indexed(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String password;
    @DBRef
    private List<Urls> urlMappings = new ArrayList<>();
}
