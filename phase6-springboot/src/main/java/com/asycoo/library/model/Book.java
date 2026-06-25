package com.asycoo.library.model;

/**
 * 图书实体
 */
public record Book(String id, String title, String author, double price, boolean available) {
}
