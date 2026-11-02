package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.enums.Role;
import com.springboot.busticketbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @PostMapping("/api/add/user")
    public void addUser(@RequestParam String username,@RequestParam String password,@RequestParam Role role)
    {
        userService.getUserObj(username,password,role);
    }
}
