package com.training.pet.Response;

import java.math.BigDecimal;

public record MonthlyExpenseResponse(
        int year,
        int month,
        BigDecimal totalAmount
) {}
