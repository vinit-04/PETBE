package com.training.pet.service.impl;

import com.training.pet.dao.CategoryRepository;
import com.training.pet.dao.ExpenseRepo;
import com.training.pet.dao.UserRepository;
import com.training.pet.entity.Category;
import com.training.pet.entity.Expense;
import com.training.pet.entity.User;
import com.training.pet.exceptions.ResourceNotFoundException;
import com.training.pet.models.ExpenseRequest;
import com.training.pet.Response.ExpenseResponse;
import com.training.pet.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseServiceImpl {

    private final ExpenseRepo expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

//    private User getLoggedInUser() {
//        return (User) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//    }

    private User getLoggedInUser() {

        UserPrincipal principal = (UserPrincipal)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userRepository.findById(principal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public ExpenseResponse addExpense(ExpenseRequest request) {
        User user = getLoggedInUser();

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Expense expense = Expense.builder()
                .amount(request.amount())
                .description(request.description())
                .expenseDate(request.expenseDate())
                .category(category)
                .user(user)
                .build();

        Expense saved = expenseRepository.save(expense);
        return mapToResponse(saved);
    }

    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
        User user = getLoggedInUser();

        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        expense.setExpenseDate(request.expenseDate());
        expense.setCategory(category);

        return mapToResponse(expense);
    }

    public void deleteExpense(Long id) {
        User user = getLoggedInUser();

        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);
    }

    public Page<ExpenseResponse> getAllExpenses(int page, int size, String sortBy) {
        User user = getLoggedInUser();

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        return expenseRepository.findAllByUser(user, pageable)
                .map(this::mapToResponse);
    }

    public ExpenseResponse getExpenseById(Long id) {
        User user = getLoggedInUser();

        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        return mapToResponse(expense);
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getCategory().getName(),
                expense.getDescription(),
                expense.getExpenseDate()
        );
    }
}
