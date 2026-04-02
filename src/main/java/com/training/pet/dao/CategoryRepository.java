package com.training.pet.dao;

import com.training.pet.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            SELECT c
            FROM Category c
            WHERE c.user.id = :userId
               OR (c.user IS NULL AND c.isDefault = true)
            """)
    List<Category> findVisibleCategoriesForUser(Long userId);

    Optional<Category> findByIdAndUserId(Long id, Long userId);

    boolean existsByNameIgnoreCaseAndUserId(String name, Long userId);
}