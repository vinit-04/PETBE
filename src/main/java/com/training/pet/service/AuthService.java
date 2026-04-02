package com.training.pet.service;

import com.training.pet.Response.AuthResponse;
import com.training.pet.dao.UserRepository;
import com.training.pet.entity.*;
import com.training.pet.models.LoginRequest;
import com.training.pet.models.RegistrationRequest;
import com.training.pet.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Transactional
    public boolean register(RegistrationRequest request) {
        try {
            if (userRepository.findByEmail(request.email()).isPresent()) {
                throw new RuntimeException("Email already exists! Please use a different email.");
            }
            var user = User.builder()
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .fullName(request.fullName())
                    .confirmPassword(request.confirmPassword())
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
//        var token = jwtService.generateToken(user);
//        return new AuthResponse(token, user.getEmail());
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        var user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        var token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getEmail());
    }
}
