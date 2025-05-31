package com.spring_short_url.URL_shortner.Controllers;

import org.springframework.web.bind.annotation.*;
import com.spring_short_url.URL_shortner.Services.MongoService;
import com.spring_short_url.URL_shortner.Models.Urls;
import com.spring_short_url.URL_shortner.Models.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import java.net.URI;

@RestController
public class MongoController {

	@Autowired
	private MongoService mongoService;

	@GetMapping("/")
	public String Home() {
		return "index";
	}

	// @GetMapping("/login")
	@PostMapping("/login")
	// @ResponseBody
	// public boolean saveUser(@RequestBody Users user) {
	// mongoService.saveUser(user);
	// return true;
	// }
	// Above code is just for debugging purpose and below one is the professional
	// one
	public ResponseEntity<String> saveUser(@RequestBody Users user) {
		user.setUrlMappings(null);
		mongoService.saveUser(user);
		return ResponseEntity.ok("User saved successfully");
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/api/short_it")
	public ResponseEntity<?> shortIt(@RequestBody Urls url) {
		try {
			Urls processedUrl = mongoService.createShortUrl(url);
			return ResponseEntity.ok(processedUrl);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating short URL: " + e.getMessage());
		}
	}

	@GetMapping("/url-shortener/{id}")
	public ResponseEntity<Void> getUrlById(@PathVariable("id") String hexCode) {
		try {
			String originalLongUrl = mongoService.getFullUrl(hexCode);
			if (originalLongUrl == null || originalLongUrl.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(originalLongUrl));
			return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
}
