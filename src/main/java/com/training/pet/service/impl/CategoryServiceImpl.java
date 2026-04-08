package com.training.pet.service.impl;

import com.training.pet.Response.CategoryResponseDto;
import com.training.pet.dao.CategoryRepository;
import com.training.pet.dao.UserRepository;
import com.training.pet.entity.Category;
import com.training.pet.entity.User;
import com.training.pet.exceptions.BadRequestException;
import com.training.pet.exceptions.ResourceNotFoundException;
import com.training.pet.models.CategoryRequestDto;
import com.training.pet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private static final String DEFAULT_CATEGORY_COLOR = "hsl(210, 80%, 55%)";

    private static final List<String> DEFAULT_CATEGORIES =
            List.of("Food", "Transportation", "Entertainment", "Bills", "Others");

    private static String defaultColorForCategory(String categoryName) {
        return switch (categoryName.toUpperCase()) {
            case "FOOD" -> "hsl(0, 70%, 55%)";
            case "TRANSPORT", "TRANSPORTATION" -> "hsl(160, 60%, 45%)";
            case "ENTERTAINMENT" -> "hsl(24, 80%, 55%)";
            case "BILLS" -> "hsl(45, 80%, 50%)";
            case "OTHERS" -> "hsl(120, 50%, 45%)";
            default -> DEFAULT_CATEGORY_COLOR;
        };
    }

    @Override
    public CategoryResponseDto create(CategoryRequestDto dto, Long userId) throws BadRequestException {

        if (categoryRepository.existsByNameIgnoreCaseAndUserId(dto.getName(), userId)) {
            throw new BadRequestException("Category already exists");
        }

        for (com.training.pet.enums.Category c : com.training.pet.enums.Category.values()) {
            if (dto.getName().contains(c.toString())) {
                throw new BadRequestException("This is the Default Category, Please enter different one!");
            }
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Category category = Category.builder()
                .name(dto.getName())
                .categoryColor(dto.getCategoryColor() != null ? dto.getCategoryColor() : DEFAULT_CATEGORY_COLOR)
                .icon(dto.getIcon())
                .isDefault(false)
                .user(user)
                .build();

        categoryRepository.save(category);

        return mapToDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAll(Long userId) {
        return categoryRepository.findVisibleCategoriesForUser(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryResponseDto update(Long categoryId, CategoryRequestDto dto, Long userId) throws BadRequestException {

        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (category.isDefault()) {
            throw new BadRequestException("Default category cannot be updated");
        }

        category.setName(dto.getName());
        if (dto.getCategoryColor() != null) {
            category.setCategoryColor(dto.getCategoryColor());
        }
        category.setIcon(dto.getIcon());

        categoryRepository.save(category);

        return mapToDto(category);
    }

    @Override
    public void delete(Long categoryId, Long userId) throws BadRequestException {

        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (category.isDefault()) {
            throw new BadRequestException("Default category cannot be deleted");
        }

        categoryRepository.delete(category);
    }

    @Override
    public void createDefaultCategoriesForUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Category> categories = DEFAULT_CATEGORIES.stream()
                .map(name -> Category.builder()
                        .name(name)
                        .categoryColor(defaultColorForCategory(name))
                        .icon(null)
                        .isDefault(true)
                        .user(user)
                        .build())
                .toList();

        categoryRepository.saveAll(categories);
    }

    private CategoryResponseDto mapToDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryColor(category.getCategoryColor())
                .icon(category.getIcon())
                .isDefault(category.isDefault())
                .build();
    }
}