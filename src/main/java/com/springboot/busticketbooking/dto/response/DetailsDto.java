package com.springboot.busticketbooking.dto.response;

import com.springboot.busticketbooking.enums.Role;

public record DetailsDto(String username,
                         Boolean isActive,
                         Role role) {
}
