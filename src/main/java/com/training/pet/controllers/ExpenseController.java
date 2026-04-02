package com.training.pet.controllers;

import com.training.pet.models.ExpenseRequest;
import com.training.pet.Response.ExpenseResponse;
import com.training.pet.service.impl.ExpenseServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseServiceImpl expenseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponse addExpense(@RequestBody @Valid ExpenseRequest request) {
        return expenseService.addExpense(request);
    }

    @PutMapping("updateExpense/{id}")
    public ExpenseResponse updateExpense(
            @PathVariable Long id,
            @RequestBody @Valid ExpenseRequest request) {
        return expenseService.updateExpense(id, request);
    }

    @DeleteMapping("deleteExpense/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/get-all-expense")
    public Page<ExpenseResponse> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy) {
        return expenseService.getAllExpenses(page, size, sortBy);
    }

    @GetMapping("getExpense/{id}")
    public ExpenseResponse getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }
}
