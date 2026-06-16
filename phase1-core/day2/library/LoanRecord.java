import java.time.LocalDate;

/**
 * 借阅记录
 */
public class LoanRecord {

    private final String bookId;
    private final String memberId;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public LoanRecord(String bookId, String memberId, LocalDate loanDate, LocalDate dueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getBookId() { return bookId; }
    public String getMemberId() { return memberId; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    /** 逾期天数，未逾期返回 0 */
    public long overdueDays(LocalDate returnDay) {
        if (!returnDay.isAfter(dueDate)) {
            return 0;
        }
        return returnDay.toEpochDay() - dueDate.toEpochDay();
    }
}
