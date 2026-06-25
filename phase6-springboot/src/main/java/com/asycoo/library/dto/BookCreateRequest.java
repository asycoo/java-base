package com.asycoo.library.dto;

/**
 * 创建图书请求体
 */
public record BookCreateRequest(String id, String title, String author, double price) {
}
