package com.asycoo.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * 创建图书请求体 — @Valid 触发字段校验
 */
public record BookCreateRequest(
        @NotBlank(message = "id 不能为空") String id,
        @NotBlank(message = "title 不能为空") String title,
        @NotBlank(message = "author 不能为空") String author,
        @Positive(message = "price 必须大于 0") double price) {
}
