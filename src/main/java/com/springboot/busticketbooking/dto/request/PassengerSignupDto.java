package com.springboot.busticketbooking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PassengerSignupDto(
        @NotBlank(message = "username is required")
        @NotNull(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        @NotNull(message = "password is required")
        String password,
        @NotBlank(message = "name is required")
        @NotNull(message = "name is required")
        String name,
        String email,
        @NotNull(message = "phone is required")
        @Size(min=10,max=10)
        @NotBlank(message = "phone is required")
        String phone
) {

}
