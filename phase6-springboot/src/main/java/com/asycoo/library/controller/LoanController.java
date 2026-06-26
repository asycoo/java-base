package com.asycoo.library.controller;

import com.asycoo.library.dto.ApiResponse;
import com.asycoo.library.dto.LoanRequest;
import com.asycoo.library.dto.ReturnResponse;
import com.asycoo.library.entity.Loan;
import com.asycoo.library.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 借阅 API
 *
 * GET  /api/loans?page=0&size=10&active=true  分页列表
 * POST /api/loans                              借书
 * POST /api/loans/return                       还书
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ApiResponse<Page<Loan>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "true") boolean active) {
        return ApiResponse.ok(loanService.listLoans(page, size, active));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Loan> borrow(@Valid @RequestBody LoanRequest request) {
        return ApiResponse.ok(loanService.borrowBook(request));
    }

    @PostMapping("/return")
    public ApiResponse<ReturnResponse> returnBook(@Valid @RequestBody LoanRequest request) {
        return ApiResponse.ok(loanService.returnBook(request));
    }
}
