package com.optimuminfosystem.pfm.user_service.controller;

import com.optimuminfosystem.pfm.user_service.model.User;
import com.optimuminfosystem.pfm.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param user User object to create
     * @return ResponseEntity containing the created User
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating user: {}", user);
        User saved = userService.createUser(user);
        return ResponseEntity.ok(saved);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id User ID
     * @return ResponseEntity containing the User
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        log.info("Fetching user with id: {}", id);
        return ResponseEntity.ok(userService.get(id));
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of Users
     */
    @GetMapping
    public ResponseEntity<List<User>> all() {
        log.info("Fetching all users");
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * Deletes a user by ID.
     *
     * @param id User ID
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
