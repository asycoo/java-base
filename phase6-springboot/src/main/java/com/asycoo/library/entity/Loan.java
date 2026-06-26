package com.asycoo.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    protected Loan() {
    }

    public Loan(Book book, Member member, LocalDate loanDate, LocalDate dueDate) {
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public String getBookId() {
        return book != null ? book.getId() : null;
    }

    public String getBookTitle() {
        return book != null ? book.getTitle() : null;
    }

    public String getMemberId() {
        return member != null ? member.getId() : null;
    }

    public String getMemberName() {
        return member != null ? member.getName() : null;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public long overdueDays(LocalDate returnDay) {
        if (!returnDay.isAfter(dueDate)) {
            return 0;
        }
        return returnDay.toEpochDay() - dueDate.toEpochDay();
    }
}
