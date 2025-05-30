package com.spring_short_url.URL_shortner.Services;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import com.spring_short_url.URL_shortner.Models.Users;
import com.spring_short_url.URL_shortner.Models.Urls;
import com.spring_short_url.URL_shortner.Repositories.UsersInterface;
import com.spring_short_url.URL_shortner.Repositories.UrlInterface;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
// import org.bson.types.ObjectId;

@Service
@Component
public class MongoService {
    private final UrlInterface urlRepo;
    private final UsersInterface userRepo;

    @Autowired
    public MongoService(UrlInterface urlRepo, UsersInterface userRepo) {
        this.urlRepo = urlRepo;
        this.userRepo = userRepo;
    }


    public void saveUser(Users user) {
        userRepo.save(user);
    }

    public void saveUrl(Urls url) {
        urlRepo.save(url);
    }

    public String getFullUrl(String hexcode){
        // ObjectId id = new ObjectId(hexcode);
        Urls url = urlRepo.findById(hexcode);
        if(url != null){
            return url.getLongURL();
        }
        return "";
    }
}
