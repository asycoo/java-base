package day3.library;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import phase4.exception.BusinessException;

/**
 * 图书管理系统 v3
 *
 * 在 v2 基础上：
 * - JSON 持久化
 * - 统一异常 errorCode（phase4.exception）
 */
public class LibraryService {

    public static final double FINE_PER_DAY = 1.0;
    private static final int LOAN_DAYS = 14;

    private final BookRepository bookRepository;
    private final Path booksFile;

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, List<Book>> booksByAuthor = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final List<LoanRecord> activeLoans = new ArrayList<>();

    public LibraryService(BookRepository bookRepository, Path booksFile) {
        this.bookRepository = bookRepository;
        this.booksFile = booksFile;
        loadBooks();
    }

    public void loadBooks() {
        books.clear();
        books.putAll(bookRepository.load(booksFile));
        rebuildAuthorIndex();
    }

    public void saveBooks() {
        bookRepository.save(booksFile, books);
    }

    private void rebuildAuthorIndex() {
        booksByAuthor.clear();
        for (Book book : books.values()) {
            booksByAuthor
                    .computeIfAbsent(book.getAuthor().toLowerCase(), k -> new ArrayList<>())
                    .add(book);
        }
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
        booksByAuthor
                .computeIfAbsent(book.getAuthor().toLowerCase(), k -> new ArrayList<>())
                .add(book);
    }

    public List<Book> listBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Book> findByAuthor(String author) {
        if (author == null || author.isBlank()) {
            return List.of();
        }
        return new ArrayList<>(booksByAuthor.getOrDefault(author.toLowerCase(), List.of()));
    }

    public List<Book> listBooksSortedByPrice() {
        List<Book> result = new ArrayList<>(books.values());
        result.sort(Comparator.comparing(Book::getPrice));
        return result;
    }

    public void registerMember(Member member) {
        members.put(member.getId(), member);
    }

    public LoanRecord borrowBook(String bookId, String memberId) {
        if (!books.containsKey(bookId)) {
            throw new BusinessException("BOOK_NOT_FOUND", "图书不存在");
        }
        if (!books.get(bookId).isAvailable()) {
            throw new BusinessException("BOOK_NOT_AVAILABLE", "图书不可借");
        }
        if (!members.containsKey(memberId)) {
            throw new BusinessException("MEMBER_NOT_FOUND", "会员不存在");
        }
        LoanRecord loan = new LoanRecord(bookId, memberId, LocalDate.now(), LocalDate.now().plusDays(LOAN_DAYS));
        books.get(bookId).setAvailable(false);
        activeLoans.add(loan);
        return loan;
    }

    public double returnBook(String bookId, String memberId) {
        LoanRecord loan = activeLoans.stream()
                .filter(l -> l.getBookId().equals(bookId) && l.getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new BusinessException("LOAN_NOT_FOUND", "借阅记录不存在"));
        loan.setReturnDate(LocalDate.now());
        books.get(bookId).setAvailable(true);
        activeLoans.remove(loan);
        return loan.overdueDays(LocalDate.now()) * FINE_PER_DAY;
    }

    public List<LoanRecord> getActiveLoans() {
        return new ArrayList<>(activeLoans);
    }
}
