package com.asycoo.library.dto;

import jakarta.validation.constraints.NotBlank;

public record LoanRequest(
        @NotBlank(message = "bookId 不能为空") String bookId,
        @NotBlank(message = "memberId 不能为空") String memberId) {
}
