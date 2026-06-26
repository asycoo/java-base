package com.asycoo.library.controller;

import com.asycoo.library.dto.ApiResponse;
import com.asycoo.library.dto.LoginRequest;
import com.asycoo.library.dto.LoginResponse;
import com.asycoo.library.security.JwtService;
import com.asycoo.library.security.MemberPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        String token = jwtService.generateToken(principal);
        return ApiResponse.ok(new LoginResponse(token, principal.getUsername(), principal.getRole()));
    }
}
