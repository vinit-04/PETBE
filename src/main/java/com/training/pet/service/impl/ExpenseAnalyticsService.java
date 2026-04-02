package com.training.pet.service.impl;

import com.training.pet.dao.ExpenseRepo;
import com.training.pet.dao.UserRepository;
import com.training.pet.entity.User;
import com.training.pet.Response.CategoryWiseExpenseResponse;
import com.training.pet.Response.MonthlyExpenseResponse;
import com.training.pet.Response.TotalExpenseResponse;
import com.training.pet.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseAnalyticsService {

    private final ExpenseRepo expenseRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        UserPrincipal principal = (UserPrincipal)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userRepository.findById(principal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public TotalExpenseResponse getTotalExpense(
            LocalDate startDate,
            LocalDate endDate
    ) {
        User user = getLoggedInUser();

        BigDecimal total = expenseRepository
                .getTotalExpenseBetweenDates(user, startDate, endDate);

        return new TotalExpenseResponse(total);
    }

    public List<MonthlyExpenseResponse> getMonthlySummary() {
        User user = getLoggedInUser();
        return expenseRepository.getMonthlySummary(user);
    }

    public List<CategoryWiseExpenseResponse> getCategoryWiseExpense() {
        User user = getLoggedInUser();
        return expenseRepository.getCategoryWiseExpense(user);
    }
}

