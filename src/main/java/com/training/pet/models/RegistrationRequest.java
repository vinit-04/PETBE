package com.training.pet.models;


public record RegistrationRequest(String email, String password, String fullName, String confirmPassword) {}
