package com.asycoo.library.repository;

import com.asycoo.library.entity.Loan;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("""
            SELECT l FROM Loan l
            JOIN FETCH l.book
            JOIN FETCH l.member
            WHERE l.book.id = :bookId AND l.member.id = :memberId AND l.returnDate IS NULL
            """)
    Optional<Loan> findActiveLoan(String bookId, String memberId);

    @Query(value = """
            SELECT l FROM Loan l
            JOIN FETCH l.book
            JOIN FETCH l.member
            WHERE l.returnDate IS NULL
            """,
            countQuery = "SELECT COUNT(l) FROM Loan l WHERE l.returnDate IS NULL")
    Page<Loan> findActiveLoans(Pageable pageable);

    @Query(value = """
            SELECT l FROM Loan l
            JOIN FETCH l.book
            JOIN FETCH l.member
            """,
            countQuery = "SELECT COUNT(l) FROM Loan l")
    Page<Loan> findAllWithDetails(Pageable pageable);
}
