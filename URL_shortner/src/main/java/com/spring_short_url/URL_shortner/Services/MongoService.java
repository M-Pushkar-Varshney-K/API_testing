package com.spring_short_url.URL_shortner.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.spring_short_url.URL_shortner.Models.Users;
import com.spring_short_url.URL_shortner.Models.Urls;
import com.spring_short_url.URL_shortner.Repositories.UsersInterface;
import com.spring_short_url.URL_shortner.Repositories.UrlInterface;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class MongoService {
    private final UrlInterface urlRepo;
    private final UsersInterface userRepo;
    @Autowired
    private userService userService;

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
        Urls saved = saveUrl(url);
        return saved;
    }

    public Urls deleteShortUrl(String hexcode) {
        Optional<Urls> url = urlRepo.findById(hexcode);
        if (url.isPresent()) {
            urlRepo.delete(url.get());
            return url.get();
        } else {
            throw new IllegalArgumentException("Url with the given id does not exist");
        }
    }

    public Urls saveUrl(Urls url) {
        Urls saved = urlRepo.save(url);
        return saved;
    }

    public String getFullUrl(String hexcode) {
        Optional<Urls> url = urlRepo.findById(hexcode);
        if (url.isPresent()) {
            return url.get().getLongURL();
        } else {
            throw new IllegalArgumentException("Url with the given id does not exist");
        }
    }

    public Optional<Urls> getUrlById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!urlRepo.existsById(id)) {
            return Optional.empty();
        }
        return urlRepo.findById(id);
    }

    public Urls updateUrlEntry(String id, Urls updatedUrlEntry) {
        Optional<Urls> existingUrl = getUrlById(id);
        if (!existingUrl.isPresent()) {
            throw new IllegalArgumentException("Url with the given id does not exist");
        }
        Urls url = existingUrl.get();
        url.setLongURL(updatedUrlEntry.getLongURL() != null && !updatedUrlEntry.getLongURL().equals("")
                ? updatedUrlEntry.getLongURL()
                : url.getLongURL());
        urlRepo.save(url);
        return url;
    }

    public Optional<List<Urls>> getAllUrlsOfUser(ObjectId id) {
        Optional<Users> user = userService.getUserById(id);
        if (user.isPresent()) {
            List<Urls> urls = user.get().getUrlMappings();
            if (urls != null && !urls.isEmpty()) {
                return Optional.of(urls);
            } else {
                throw new IllegalArgumentException("No URLs found for user with ID: " + id);
            }
        }
        return Optional.empty();
    }
}
