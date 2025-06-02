package com.spring_short_url.URL_shortner.Controllers;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring_short_url.URL_shortner.Models.Users;
import com.spring_short_url.URL_shortner.Services.userService;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private userService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAll() {
        try {
            Optional<List<Users>> userEntries = userService.getAllUserEntries();
            if (userEntries.isPresent() && !userEntries.get().isEmpty()) {
                return ResponseEntity.ok(userEntries.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching users: " + e.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> getUser(@RequestBody Users user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password must not be null");
            }
            Optional<Users> existingUser = userService.getUserByEmailAndPassword(user);
            if (existingUser.isPresent()) {
                return ResponseEntity.ok(existingUser.get());
            } else {
                return ResponseEntity.status(401).body("Invalid email or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during login: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        try {
            Optional<Users> createdUser = userService.createUserEntry(user);
            if (createdUser.isPresent()) {
                return ResponseEntity.status(201).body(createdUser.get());
            } else {
                return ResponseEntity.status(400).body("User with the given email already exists");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id) {
        try {
            Optional<Users> user = userService.getUserById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(404).body("User not found or doesn't exist");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching user: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable ObjectId id, @RequestBody Users user) {
        try {
            Optional<Users> existingUser = userService.getUserById(id);
            if (!existingUser.isPresent()) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(userService.updateUserEntry(id, user));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        try {
            if (userService.deleteUserById(id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting user: " + e.getMessage());
        }
    }

}
