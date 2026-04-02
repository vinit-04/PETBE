package com.training.pet.controllers;

import com.training.pet.Response.CategoryResponseDto;
import com.training.pet.entity.Category;
import com.training.pet.models.CategoryRequestDto;
import com.training.pet.security.UserPrincipal;
import com.training.pet.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pet/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add/category")
    public CategoryResponseDto create(
            @Valid @RequestBody CategoryRequestDto dto,
            @AuthenticationPrincipal UserPrincipal user) {

        return categoryService.create(dto, user.getId());
    }

    @GetMapping("/getUserCategories")
    public List<CategoryResponseDto> getAllUsersCategories(
            @AuthenticationPrincipal UserPrincipal user) {

        return categoryService.getAll(user.getId());
    }
    @GetMapping("/get-all/categories")
    public List<Category> getAll() {

        return categoryService.getAll();
    }

    @PutMapping("/update-category/{id}")
    public CategoryResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto dto,
            @AuthenticationPrincipal UserPrincipal user) {

        return categoryService.update(id, dto, user.getId());
    }

    @DeleteMapping("/delete-category/{id}")
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal user) {

        categoryService.delete(id, user.getId());
    }
}

