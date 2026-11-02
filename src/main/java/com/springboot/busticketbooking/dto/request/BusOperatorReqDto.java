package com.springboot.busticketbooking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BusOperatorReqDto(@NotNull(message = "name is required")
                                @NotBlank(message = "name is required")
                                String name,
                                @NotNull(message = "email is required")
                                @NotBlank(message = "email is required")
                                String email,
                                @NotBlank(message = "phone is required")
                                @NotNull(message = "phone is required")
                                @Size(min=0,max=10)
                                String phone,
                                @NotNull(message = "username is required")
                                @NotBlank(message = "username is required")
                                String username,
                                @NotBlank(message = "password is required")
                                @NotNull(message = "password is required")
                                String password) {
}
