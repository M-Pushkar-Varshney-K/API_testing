package com.spring_short_url.URL_shortner.Models;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public class Urls {
    @Id
    private String id;
    private String longURL;
    private String shortURL;
    private LocalDateTime createdAt;
    private LocalDateTime expired;

    // Constructors
    public Urls() {}

    public Urls(String id, String longURL, String shortURL, LocalDateTime createdAt, LocalDateTime expired) {
        this.id = id;
        this.longURL = longURL;
        this.shortURL = shortURL;
        this.createdAt = createdAt;
        this.expired = expired;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLongURL() { return longURL; }
    public void setLongURL(String longURL) { this.longURL = longURL; }

    public String getShortURL() { return shortURL; }
    public void setShortURL(String shortURL) { this.shortURL = shortURL; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpired() { return expired; }
    public void setExpired(LocalDateTime expired) { this.expired = expired; }
}
