package com.training.pet.controllers;

import com.training.pet.Response.ApiResponse;
import com.training.pet.Response.AuthResponse;
import com.training.pet.models.LoginRequest;
import com.training.pet.models.RegistrationRequest;
import com.training.pet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegistrationRequest request) {
        ApiResponse response = new ApiResponse();
        System.out.print(request.toString());
        boolean status = authService.register(request);
        if(status){
            response.setMessage("User registered successfully");
        }
        else{
            response.setMessage("Something is wrong!!");
        }

        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
