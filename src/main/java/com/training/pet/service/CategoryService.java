package com.training.pet.service;

import com.training.pet.Response.CategoryResponseDto;
import com.training.pet.entity.Category;
import com.training.pet.exceptions.BadRequestException;
import com.training.pet.models.CategoryRequestDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto create(CategoryRequestDto dto, Long userId) throws BadRequestException;

    List<CategoryResponseDto> getAll(Long userId);

    List<Category> getAll();

    CategoryResponseDto update(Long categoryId, CategoryRequestDto dto, Long userId) throws BadRequestException;

    void delete(Long categoryId, Long userId) throws BadRequestException;

    void createDefaultCategoriesForUser(Long userId);
}

