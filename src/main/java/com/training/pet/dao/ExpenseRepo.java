package com.training.pet.dao;

import com.training.pet.Response.CategoryWiseExpenseResponse;
import com.training.pet.Response.MonthlyExpenseResponse;
import com.training.pet.entity.Expense;
import com.training.pet.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    Page<Expense> findAllByUser(User user, Pageable pageable);

    Optional<Expense> findByIdAndUser(Long id, User user);

    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.user = :user
        AND e.expenseDate BETWEEN :startDate AND :endDate
    """)
    BigDecimal getTotalExpenseBetweenDates(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("""
        SELECT new com.training.pet.Response.MonthlyExpenseResponse(
            YEAR(e.expenseDate),
            MONTH(e.expenseDate),
            SUM(e.amount)
        )
        FROM Expense e
        WHERE e.user = :user
        GROUP BY YEAR(e.expenseDate), MONTH(e.expenseDate)
        ORDER BY YEAR(e.expenseDate), MONTH(e.expenseDate)
    """)
    List<MonthlyExpenseResponse> getMonthlySummary(User user);

    @Query("""
        SELECT new com.training.pet.Response.CategoryWiseExpenseResponse(
            e.category.name,
            SUM(e.amount)
        )
        FROM Expense e
        WHERE e.user = :user
        GROUP BY e.category.name
    """)
    List<CategoryWiseExpenseResponse> getCategoryWiseExpense(User user);
}
