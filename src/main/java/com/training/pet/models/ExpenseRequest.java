package com.training.pet.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(

        @NotNull
        @Positive
        BigDecimal amount,

        @NotNull
        Long categoryId,

        String description,

        @NotNull
        LocalDate expenseDate
) {}
