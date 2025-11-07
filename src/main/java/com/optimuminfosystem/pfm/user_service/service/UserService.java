package com.optimuminfosystem.pfm.user_service.service;

import com.optimuminfosystem.pfm.user_service.exception.UserServiceException;
import com.optimuminfosystem.pfm.user_service.model.User;
import com.optimuminfosystem.pfm.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user if the user does not already exist.
     *
     * @param user User object to create
     * @return Saved User object
     * @throws UserServiceException if user with the same ID already exists
     */
    public User createUser(User user) {
        log.info("Creating user: {}", user);
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("User with id {} already exists", user.getId());
            throw new UserServiceException(
                    "USER_EXISTS",
                    "User with this ID already exists",
                    HttpStatus.CONFLICT
            );
        }
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id User ID
     * @return User object
     * @throws UserServiceException if user not found
     */
    public User get(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserServiceException("NOT FOUND", "USER not available for given id ", HttpStatus.NO_CONTENT));

    }

    /**
     * Retrieves all users.
     *
     * @return List of User objects
     */
    public List<User> getAll() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    /**
     * Deletes a user by ID.
     *
     * @param id User ID to delete
     * @throws UserServiceException if user not found or deletion fails
     */
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        try {
            if (!userRepository.existsById(id)) {
                log.warn("User with id {} not found", id);
                throw new UserServiceException(
                        "USER_NOT_FOUND",
                        "User not found with id: " + id,
                        HttpStatus.NOT_FOUND
                );
            }

            userRepository.deleteById(id);
            log.info("User with id {} deleted successfully", id);
        } catch (DataAccessException exception) {
            log.error("Failed to delete user with id {}: {}", id, exception.getMessage());
            throw new UserServiceException(
                    "DATABASE_ERROR",
                    "Failed to delete user: " + exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
