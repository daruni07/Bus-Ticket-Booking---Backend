package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.response.DetailsDto;
import com.springboot.busticketbooking.dto.response.TokenDto;
import com.springboot.busticketbooking.service.UserService;
import com.springboot.busticketbooking.utility.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class AuthController {
    private final JwtUtility jwtUtility;
    private final UserService userService;
    @GetMapping("/api/auth/login")
    public TokenDto login(Principal principal){
        String loggedInUsername = principal.getName();
        String token = jwtUtility.generateToken(loggedInUsername);
        return new TokenDto(token,
                jwtUtility.extractExpiration(token),
                LocalDateTime.now().toString());}
    @GetMapping("/api/auth/user-details")
    public DetailsDto getUserDetails(Principal principal){
        String loggedInUsername = principal.getName();
        return userService.getUserDetails(loggedInUsername);
    }
}
