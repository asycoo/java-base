package com.asycoo.library.repository;

import com.asycoo.library.entity.Loan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByBookIdAndMemberIdAndReturnDateIsNull(String bookId, String memberId);

    List<Loan> findByReturnDateIsNull();
}
