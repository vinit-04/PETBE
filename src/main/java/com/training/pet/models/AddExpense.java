package com.training.pet.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddExpense {

    private double amount;
    private String category;
    private String description;
    private String paymentType;
    private Date date;
}
