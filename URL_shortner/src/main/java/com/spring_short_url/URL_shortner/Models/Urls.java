package com.spring_short_url.URL_shortner.Models;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class Urls {
    @Id
    private String id;
    @NonNull
    private String longURL;
    private String shortURL;
    private LocalDateTime createdAt;
    private LocalDateTime expired;
}
