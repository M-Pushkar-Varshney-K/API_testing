package com.spring_short_url.URL_shortner.Services;

// import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring_short_url.URL_shortner.Models.Users;
import com.spring_short_url.URL_shortner.Repositories.UsersInterface;

@Component
public class userService {
    @Autowired
    private UsersInterface usersInterface;

    public void saveUserEntry(Users user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        } else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        } else if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        usersInterface.save(user);
    }

    public Optional<Users> createUserEntry(Users user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        } else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        } else if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        } else if (!usersInterface.findByEmail(user.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("User with the given email already exists");
        } else {
            saveUserEntry(user);
            return Optional.of(user);
        }
    }

    public Optional<List<Users>> getAllUserEntries() {
        List<Users> userEntries = usersInterface.findAll();
        if (userEntries.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userEntries);
    }

    public Optional<Users> getUserById(ObjectId id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!usersInterface.existsById(id)) {
            return Optional.empty();
        }
        return usersInterface.findById(id);
    }

    public boolean deleteUserById(ObjectId id) {
        Optional<Users> existingUser = getUserById(id);
        if (existingUser.isPresent()) {
            usersInterface.delete(existingUser.get());
            return true;
        }
        throw new IllegalArgumentException("User with the given ID does not exist");
    }

    public Users updateUserEntry(ObjectId id, Users updatedUserEntry) {
        Optional<Users> existingUserOpt = getUserById(id);
        if (existingUserOpt.isPresent()) {
            Users existingUser = existingUserOpt.get();
            existingUser
                    .setName(updatedUserEntry.getName() != null && !updatedUserEntry.getName().equals("")
                            ? updatedUserEntry.getName()
                            : existingUser.getName());
            existingUser
                    .setPassword(updatedUserEntry.getPassword() != null && !updatedUserEntry.getPassword().equals("")
                            ? updatedUserEntry.getPassword()
                            : existingUser.getPassword());

            existingUser.setEmail(updatedUserEntry.getEmail() != null && !updatedUserEntry.getEmail().equals("")
                    ? updatedUserEntry.getEmail()
                    : existingUser.getEmail());

            usersInterface.save(existingUser);
            return existingUser;
        }
        throw new IllegalArgumentException("User with the given ID does not exist");
    }

    public Optional<Users> getUserByEmailAndPassword(Users user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        Optional<Users> existingUser = usersInterface.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (existingUser.get().getPassword().equals(user.getPassword())) {
                return existingUser;
            } else {
                throw new IllegalArgumentException("Incorrect password for the given email");
            }
        } else {
            throw new IllegalArgumentException("User with the given email and password does not exist");
        }
    }
}
