package com.optimuminfosystem.pfm.user_service.repository;

import com.optimuminfosystem.pfm.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 * Provides CRUD operations and query methods for User objects.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Check if a user exists with the given email.
     *
     * @param email User's email
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
}

