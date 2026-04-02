package com.training.pet.controllers;

import com.training.pet.Response.CategoryWiseExpenseResponse;
import com.training.pet.Response.MonthlyExpenseResponse;
import com.training.pet.Response.TotalExpenseResponse;
import com.training.pet.service.impl.ExpenseAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet/api/v1/expenses/analytics")
@RequiredArgsConstructor
public class ExpenseAnalyticsController {

    private final ExpenseAnalyticsService analyticsService;

    // 1 Total expense between date range
    @GetMapping("/total")
    public TotalExpenseResponse getTotalExpense(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return analyticsService.getTotalExpense(startDate, endDate);
    }

    // 2 Monthly expense summary
    @GetMapping("/monthly")
    public List<MonthlyExpenseResponse> getMonthlySummary() {
        List<MonthlyExpenseResponse> list  = analyticsService.getMonthlySummary();
        list.stream().filter(item -> item!=null).collect(Collectors.toList());
        return analyticsService.getMonthlySummary();
    }

    // 3 Category-wise expense breakdown
    @GetMapping("/category-wise")
    public List<CategoryWiseExpenseResponse> getCategoryWiseExpense() {
        return analyticsService.getCategoryWiseExpense();
    }
}
