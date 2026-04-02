package com.training.pet.dao;

import com.training.pet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Used during login to find the user by their email
    Optional<User> findByEmail(String email);

    // Used during registration to check if email is already taken
    Boolean existsByEmail(String email);
}
