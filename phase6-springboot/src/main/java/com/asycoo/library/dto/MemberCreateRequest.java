package com.asycoo.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberCreateRequest(
        @NotBlank(message = "id 不能为空") String id,
        @NotBlank(message = "name 不能为空") String name,
        @NotBlank(message = "username 不能为空") String username,
        @NotBlank(message = "password 不能为空") @Size(min = 6, message = "password 至少 6 位") String password) {
}
