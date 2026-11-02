package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.AdminDto;
import com.springboot.busticketbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/api/admin/add")
    public void addAdmin(@RequestBody AdminDto adminDto){
        userService.addAdmin(adminDto);}
}
