package com.training.pet.dao;

import com.training.pet.entity.Expense;
import com.training.pet.models.AddExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ExpenseDao {
    public Expense addExpense(Expense expense);
    public Expense updateExpense(int id, AddExpense expense);
}
