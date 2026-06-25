package com.asycoo.library.controller;

import com.asycoo.library.dto.ApiResponse;
import com.asycoo.library.dto.BookCreateRequest;
import com.asycoo.library.model.Book;
import com.asycoo.library.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实操 6.2 — 图书 REST CRUD
 *
 * GET    /api/books       列表
 * GET    /api/books/{id}  详情
 * POST   /api/books       新增
 * DELETE /api/books/{id}  删除
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ApiResponse<List<Book>> list() {
        return ApiResponse.ok(bookService.listBooks());
    }

    @GetMapping("/{id}")
    public ApiResponse<Book> get(@PathVariable String id) {
        return ApiResponse.ok(bookService.getBook(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Book> create(@Valid @RequestBody BookCreateRequest request) {
        return ApiResponse.ok(bookService.createBook(request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        bookService.deleteBook(id);
        return ApiResponse.ok(null);
    }
}
