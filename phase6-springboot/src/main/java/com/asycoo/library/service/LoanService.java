package com.asycoo.library.service;

import com.asycoo.library.dto.LoanRequest;
import com.asycoo.library.dto.ReturnResponse;
import com.asycoo.library.entity.Book;
import com.asycoo.library.entity.Loan;
import com.asycoo.library.entity.Member;
import com.asycoo.library.exception.BusinessException;
import com.asycoo.library.repository.BookRepository;
import com.asycoo.library.repository.LoanRepository;
import com.asycoo.library.repository.MemberRepository;
import java.time.LocalDate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

    public static final double FINE_PER_DAY = 1.0;
    private static final int LOAN_DAYS = 14;

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public LoanService(BookRepository bookRepository, MemberRepository memberRepository,
                       LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional(readOnly = true)
    public Page<Loan> listLoans(int page, int size, boolean activeOnly) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "loanDate"));
        if (activeOnly) {
            return loanRepository.findActiveLoans(pageable);
        }
        return loanRepository.findAllWithDetails(pageable);
    }

    @Transactional
    @CacheEvict(value = BookService.BOOKS_CACHE, allEntries = true)
    public Loan borrowBook(LoanRequest request) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new BusinessException("BOOK_NOT_FOUND", "图书不存在: " + request.bookId()));
        if (!book.isAvailable()) {
            throw new BusinessException("BOOK_NOT_AVAILABLE", "图书不可借: " + request.bookId());
        }
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new BusinessException("MEMBER_NOT_FOUND", "会员不存在: " + request.memberId()));

        book.setAvailable(false);
        bookRepository.save(book);

        Loan loan = new Loan(book, member, LocalDate.now(), LocalDate.now().plusDays(LOAN_DAYS));
        return loanRepository.save(loan);
    }

    @Transactional
    @CacheEvict(value = BookService.BOOKS_CACHE, allEntries = true)
    public ReturnResponse returnBook(LoanRequest request) {
        Loan loan = loanRepository.findActiveLoan(request.bookId(), request.memberId())
                .orElseThrow(() -> new BusinessException("LOAN_NOT_FOUND", "借阅记录不存在"));

        LocalDate today = LocalDate.now();
        double fine = loan.overdueDays(today) * FINE_PER_DAY;
        loan.setReturnDate(today);
        loanRepository.save(loan);

        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new BusinessException("BOOK_NOT_FOUND", "图书不存在: " + request.bookId()));
        book.setAvailable(true);
        bookRepository.save(book);

        return new ReturnResponse(fine, loan);
    }
}
