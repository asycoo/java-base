package com.asycoo.library.dto;

import com.asycoo.library.entity.Role;

public record MemberResponse(String id, String name, String username, Role role) {
}
