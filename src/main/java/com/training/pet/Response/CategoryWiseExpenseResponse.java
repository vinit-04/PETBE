package com.training.pet.Response;

import java.math.BigDecimal;

public record CategoryWiseExpenseResponse(
        String categoryName,
        BigDecimal totalAmount
) {}

