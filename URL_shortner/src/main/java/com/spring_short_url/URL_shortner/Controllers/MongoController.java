package com.spring_short_url.URL_shortner.Controllers;

import org.springframework.web.bind.annotation.*;
import com.spring_short_url.URL_shortner.Services.MongoService;
import com.spring_short_url.URL_shortner.Services.userService;
import com.spring_short_url.URL_shortner.Models.Urls;
import com.spring_short_url.URL_shortner.Models.Users;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class MongoController {

	@Autowired
	private MongoService mongoService;

	@Autowired
	private userService userService;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{id}")
	public ResponseEntity<?> home(@PathVariable("id") String hexCode) {
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

	@GetMapping("/urls/{id}")
	public ResponseEntity<?> getAllUrlsOfUserEntity(@PathVariable ObjectId id) {
		try {
			Optional<List<Urls>> urls = mongoService.getAllUrlsOfUser(id);
			if (urls.isPresent()) {
				return new ResponseEntity<>(urls.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No URLs found for user with ID: " + id, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error fetching URLs: " + e.getMessage());
		}
	}

	@PostMapping("/{id}/short_it")
	public ResponseEntity<?> shortAndStoreUrl(@PathVariable ObjectId id, @RequestBody Urls url) {
		try {
			if (url.getLongURL() == null || url.getLongURL().isEmpty()) {
				return ResponseEntity.badRequest().body("Long URL cannot be null or empty.");
			}
			Optional<Users> user = userService.getUserById(id);
			if (user.isPresent()) {
				Urls processedUrl = mongoService.createShortUrl(url);
				user.get().getUrlMappings().add(processedUrl);
				userService.saveUserEntry(user.get());
				return ResponseEntity.ok(processedUrl);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("User with ID: " + id + " not found.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating short URL: " + e.getMessage());
		}
	}

	@GetMapping("/url/{id}")
	public ResponseEntity<?> getUrlByIdEntity(@PathVariable String id) {
		try {
			Optional<Urls> url = mongoService.getUrlById(id);
			if (url.isPresent()) {
				return ResponseEntity.ok(url.get());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("URL with ID: " + id + " not found.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error fetching URL: " + e.getMessage());
		}
	}

	@PutMapping("update/{id}")
	public ResponseEntity<?> updateUrlString(@PathVariable String id, @RequestBody Urls url) {
		try {
			Optional<Urls> existingUrl = mongoService.getUrlById(id);
			if (existingUrl.isPresent()) {
				return ResponseEntity.ok(mongoService.updateUrlEntry(id, url));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("URL with ID: " + id + " not found.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}

	}

	@DeleteMapping("/delete/{id}/{uid}")
	public ResponseEntity<?> deleteUrl(@PathVariable ObjectId id, @PathVariable String uid) {
		try {
			Optional<Users> user = userService.getUserById(id);
			if (user.isPresent()) {
				List<Urls> urls = user.get().getUrlMappings();
				Optional<Urls> urlToDelete = urls.stream()
						.filter(url -> url.getId().equals(uid))
						.findFirst();
				if (urlToDelete.isPresent()) {
					mongoService.deleteShortUrl(uid);
					urls.remove(urlToDelete.get());
					userService.saveUserEntry(user.get());
					return ResponseEntity.ok("URL with ID: " + uid + " deleted successfully.");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("URL with ID: " + uid + " not found for user with ID: " + id);
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("User with ID: " + id + " not found.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating short URL: " + e.getMessage());
		}
	}

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
