import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实操 2.3 - 图书管理服务
 *
 * 要求：
 * - books: Map<String, Book>
 * - members: Map<String, Member>
 * - activeLoans: List<LoanRecord>（未归还的借阅）
 * - borrowBook / returnBook / listBooks / registerMember
 * - 逾期罚款：1 元/天（常量 FINE_PER_DAY）
 */
public class LibraryService {

    public static final double FINE_PER_DAY = 1.0;
    private static final int LOAN_DAYS = 14;

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final List<LoanRecord> activeLoans = new ArrayList<>();

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void registerMember(Member member) {
        members.put(member.getId(), member);
    }

    public List<Book> listBooks() {
        return new ArrayList<Book>(books.values());
    }

    /**
     * 借书：书必须存在且可借，会员必须存在
     */
    public LoanRecord borrowBook(String bookId, String memberId) {
        // TODO: 校验 → 创建 LoanRecord → 书设为不可借 → 加入 activeLoans
        if (!books.containsKey(bookId)) throw new LibraryException("图书不存在");
        if (!books.get(bookId).isAvailable()) throw new LibraryException("图书不可借");
        if (!members.containsKey(memberId)) throw new LibraryException("会员不存在");
        LoanRecord loanRecord = new LoanRecord(bookId, memberId, LocalDate.now(), LocalDate.now().plusDays(LOAN_DAYS));
        books.get(bookId).setAvailable(false);
        activeLoans.add(loanRecord);
        return loanRecord;
        // throw new UnsupportedOperationException("TODO: 实现 borrowBook");
    }

    /**
     * 还书：计算逾期罚款（如有），书恢复可借
     * @return 逾期罚款金额
     */
    public double returnBook(String bookId, String memberId) {
        // TODO: 找到对应 activeLoan → 标记归还 → 书恢复可借 → 返回罚款
        if (!activeLoans.stream().anyMatch(loan -> loan.getBookId().equals(bookId) && loan.getMemberId().equals(memberId))) throw new LibraryException("借阅记录不存在");
        LoanRecord loanRecord = activeLoans.stream().filter(loan -> loan.getBookId().equals(bookId) && loan.getMemberId().equals(memberId)).findFirst().orElseThrow(() -> new LibraryException("借阅记录不存在"));
        loanRecord.setReturnDate(LocalDate.now());
        books.get(bookId).setAvailable(true);
        activeLoans.remove(loanRecord);
        return loanRecord.overdueDays(LocalDate.now()) * FINE_PER_DAY;
        // throw new UnsupportedOperationException("TODO: 实现 returnBook");
    }

    public List<LoanRecord> getActiveLoans() {
        return new ArrayList<>(activeLoans);
    }
}
