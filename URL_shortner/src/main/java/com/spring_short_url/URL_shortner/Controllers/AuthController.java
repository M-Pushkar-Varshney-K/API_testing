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
        Optional<List<Users>> userEntries = userService.getAllUserEntries();
        if (userEntries.isPresent() && !userEntries.get().isEmpty()) {
            return ResponseEntity.ok(userEntries.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/login")
    public boolean getUser(@RequestBody Users user) {
        // This method is a placeholder and does not perform any authentication logic.
        return true;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        try {
            userService.saveUserEntry(user);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable ObjectId id) {
        Optional<Users> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable ObjectId id, @RequestBody Users user) {
        Optional<Users> existingUser = userService.getUserById(id);
        if (!existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userService.updateUserEntry(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        if (userService.deleteUserById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
