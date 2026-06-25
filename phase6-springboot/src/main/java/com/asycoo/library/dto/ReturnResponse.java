package com.asycoo.library.dto;

import com.asycoo.library.entity.Loan;

public record ReturnResponse(double fine, Loan loan) {
}
