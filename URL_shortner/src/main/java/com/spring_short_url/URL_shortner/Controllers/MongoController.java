package com.spring_short_url.URL_shortner.Controllers;

// import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import com.spring_short_url.URL_shortner.Services.MongoService;
import com.spring_short_url.URL_shortner.Models.Urls;
import com.spring_short_url.URL_shortner.Models.Users;

// import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import java.net.URI;
// import org.springframework.stereotype.Controller;


@RestController
public class MongoController {

	@Autowired
	private MongoService mongoService;

	@GetMapping("/")
	public String Home(){
		return "index";
	}

	// @GetMapping("/login")
	@PostMapping("/login")
	// @ResponseBody
	// public boolean saveUser(@RequestBody Users user) {
	// 	mongoService.saveUser(user);
	// 	return true;
	// }
	// Above code is just for debugging purpose and below one is the professional one
	public ResponseEntity<String> saveUser(@RequestBody Users user) {
		user.setUrlMappings(null);
	    mongoService.saveUser(user);
	    return ResponseEntity.ok("User saved successfully");
	}

	@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/api/short_it")
    public ResponseEntity<Urls> shortIt(@RequestBody Urls url) {
        url.setCreatedAt(LocalDateTime.now());
        String code = Integer.toHexString(url.getLongURL().hashCode()).toString();
        String shortUrl = "http://localhost:8080/url-shortener/" + code;
        url.setShortURL(shortUrl);
        url.setId(code);
        url.setExpired(url.getCreatedAt().plusDays(1));
        mongoService.saveUrl(url);
		// String shortUrl = "http://localhost:8080/url-shortener/" + url.getId();
        return ResponseEntity.ok(url);
    }

	@GetMapping("/url-shortener/{id}")
	public ResponseEntity<Void> getUrlById(@PathVariable("id") String hexCode){
		String originalLongUrl = mongoService.getFullUrl(hexCode);
		if(originalLongUrl == null) {
			return ResponseEntity.notFound().build();
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(originalLongUrl));
			return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		}
	}
}
