package com.spring_short_url.URL_shortner.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import com.spring_short_url.URL_shortner.Models.Users;
import com.spring_short_url.URL_shortner.Models.Urls;
import com.spring_short_url.URL_shortner.Repositories.UsersInterface;
import com.spring_short_url.URL_shortner.Repositories.UrlInterface;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class MongoService {
    private final UrlInterface urlRepo;
    private final UsersInterface userRepo;

    public MongoService(UrlInterface urlRepo, UsersInterface userRepo) {
        this.urlRepo = urlRepo;
        this.userRepo = userRepo;
    }

    public void saveUser(Users user) {
        userRepo.save(user);
    }

    public Urls createShortUrl(Urls url) {
        url.setCreatedAt(LocalDateTime.now());
        String code = Integer.toHexString(url.getLongURL().hashCode());
        String shortUrl = "http://localhost:8080/" + code;
        url.setShortURL(shortUrl);
        url.setId(code);
        url.setExpired(url.getCreatedAt().plusDays(1));
        saveUrl(url);
        return url;
    }

    public void saveUrl(Urls url) {
        urlRepo.save(url);
    }

    public String getFullUrl(String hexcode) {
        Optional<Urls> url = urlRepo.findById(hexcode);
        if (url.isPresent()) {
            return url.get().getLongURL();
        }
        return "";
    }
}
