package com.asycoo.library.dto;

import com.asycoo.library.entity.Role;

public record LoginResponse(String token, String username, Role role) {
}
