package org.example.trainplanet.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User repository - Database layer
 */
@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    @Query("SELECT s FROM User s WHERE s.email = ?1") // optional
    Optional<User> findUserByEmail(String email);
}
