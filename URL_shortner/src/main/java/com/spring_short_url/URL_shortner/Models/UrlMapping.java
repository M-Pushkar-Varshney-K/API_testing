package com.spring_short_url.URL_shortner.Models;

import java.time.LocalDateTime;

public class UrlMapping {
    

    private String longURL;
    private LocalDateTime createdAt;


    public UrlMapping(){
        this.createdAt = LocalDateTime.now();
    }
    public UrlMapping(String longURL, LocalDateTime createdAt) {
        this.longURL = longURL;
        this.createdAt = createdAt;
    }



    // Getters & Setters

    public String getLongURL() {
        return longURL;
    }
    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        if(createdAt == null){
            this.createdAt = createdAt;
        }
    }

}
