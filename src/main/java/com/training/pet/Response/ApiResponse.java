package com.training.pet.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    private String message;
    private String error;
    private String code;
}
