package com.spring_short_url.URL_shortner.Models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class Users {
    @Id
    private ObjectId id;  
    private String name;
    private String email;
    private String password;
    private List<UrlMapping> urlMappings;

    public Users() {}
    public Users(ObjectId id, String name, String email, String password, List<UrlMapping> urlMappings) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.urlMappings = urlMappings;
    }


    // Getters & Setters
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<UrlMapping> getUrlMappings() {
        return urlMappings;
    }
    public void setUrlMappings(List<UrlMapping> urlMappings) {
        this.urlMappings = urlMappings;
    }

}
