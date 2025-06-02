package com.spring_short_url.URL_shortner.Models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UrlMapping {
    private String longURL;
    private LocalDateTime createdAt;
}
