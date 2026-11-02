package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.AdminDto;
import com.springboot.busticketbooking.enums.Role;
import com.springboot.busticketbooking.model.User;
import com.springboot.busticketbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserObj(String username, String password, Role role){
        User user =  new User(username,passwordEncoder.encode(password),role);
        return userRepository.save(user);
    }
    public void addAdmin(AdminDto adminDto) {
        User user = new User(
                adminDto.username(),
                passwordEncoder.encode(adminDto.password()),
                Role.ADMIN
        );

        userRepository.save(user);
    }
}
