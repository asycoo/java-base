package day3.library;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实操 3.2 - 图书管理系统 v2
 *
 * 在 v1 基础上新增：
 * - BookRepository 持久化
 * - booksByAuthor 作者索引
 * - findByAuthor / listBooksSortedByPrice
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

    /** 从文件加载图书，并重建作者索引 */
    public void loadBooks() {
        // TODO: books.clear() → bookRepository.load → putAll → rebuildAuthorIndex()
        books.clear();
        books.putAll(bookRepository.load(booksFile));
        rebuildAuthorIndex();
        // throw new UnsupportedOperationException("TODO: 实现 loadBooks");
    }

    /** 保存图书到文件 */
    public void saveBooks() {
        // TODO: bookRepository.save(booksFile, books)
        bookRepository.save(booksFile, books);
        // throw new UnsupportedOperationException("TODO: 实现 saveBooks");
    }

    /** 重建 booksByAuthor 索引（作者 key 统一小写，便于忽略大小写搜索） */
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

    /** 按作者查书（忽略大小写） */
    public List<Book> findByAuthor(String author) {
        if (author == null || author.isBlank()) {
            return List.of();
        }
        return new ArrayList<>(booksByAuthor.getOrDefault(author.toLowerCase(), List.of()));
    }

    /** 按价格从低到高排序 */
    public List<Book> listBooksSortedByPrice() {
        // TODO: 复制 values，Comparator.comparing(Book::getPrice)
        List<Book> result = new ArrayList<>(books.values());
        result.sort(Comparator.comparing(Book::getPrice));
        return result;
        // throw new UnsupportedOperationException("TODO: 实现 listBooksSortedByPrice");
    }

    public void registerMember(Member member) {
        members.put(member.getId(), member);
    }

    public LoanRecord borrowBook(String bookId, String memberId) {
        if (!books.containsKey(bookId)) throw new LibraryException("图书不存在");
        if (!books.get(bookId).isAvailable()) throw new LibraryException("图书不可借");
        if (!members.containsKey(memberId)) throw new LibraryException("会员不存在");
        LoanRecord loan = new LoanRecord(bookId, memberId, LocalDate.now(), LocalDate.now().plusDays(LOAN_DAYS));
        books.get(bookId).setAvailable(false);
        activeLoans.add(loan);
        return loan;
    }

    public double returnBook(String bookId, String memberId) {
        LoanRecord loan = activeLoans.stream()
                .filter(l -> l.getBookId().equals(bookId) && l.getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new LibraryException("借阅记录不存在"));
        loan.setReturnDate(LocalDate.now());
        books.get(bookId).setAvailable(true);
        activeLoans.remove(loan);
        return loan.overdueDays(LocalDate.now()) * FINE_PER_DAY;
    }

    public List<LoanRecord> getActiveLoans() {
        return new ArrayList<>(activeLoans);
    }
}
