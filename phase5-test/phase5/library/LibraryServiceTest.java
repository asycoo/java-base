package phase5.library;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import day3.library.Book;
import day3.library.BookRepository;
import day3.library.LibraryService;
import day3.library.LoanRecord;
import day3.library.Member;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import phase4.exception.BusinessException;

/**
 * 实操 5.1 — LibraryService 单元测试（JUnit 5 + Mockito）
 *
 * Mock BookRepository，不依赖真实 books.json 文件。
 */
@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    private LibraryService library;
    private final Path booksFile = Path.of("test-books.json");

    @BeforeEach
    void setUp() {
        Map<String, Book> books = new HashMap<>();
        books.put("B001", new Book("B001", "Java核心技术", "Horstmann", 128.0));
        books.put("B002", new Book("B002", "Effective Java", "Bloch", 89.0));
        when(bookRepository.load(booksFile)).thenReturn(books);

        library = new LibraryService(bookRepository, booksFile);
        library.registerMember(new Member("M001", "张三"));
    }

    @Test
    void loadBooks_应从仓库加载图书() {
        assertEquals(2, library.listBooks().size());
    }

    @Test
    void borrowBook_成功_书变为不可借() {
        LoanRecord loan = library.borrowBook("B001", "M001");

        assertEquals("B001", loan.getBookId());
        assertEquals("M001", loan.getMemberId());
        assertFalse(findBook("B001").isAvailable());
        assertEquals(1, library.getActiveLoans().size());
    }

    @Test
    void borrowBook_书不存在_抛BOOK_NOT_FOUND() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> library.borrowBook("B999", "M001"));

        assertEquals("BOOK_NOT_FOUND", ex.getErrorCode());
        assertEquals("图书不存在", ex.getMessage());
    }

    @Test
    void borrowBook_书已借出_抛BOOK_NOT_AVAILABLE() {
        library.borrowBook("B001", "M001");

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> library.borrowBook("B001", "M001"));

        assertEquals("BOOK_NOT_AVAILABLE", ex.getErrorCode());
    }

    @Test
    void returnBook_无借阅记录_抛LOAN_NOT_FOUND() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> library.returnBook("B001", "M001"));

        assertEquals("LOAN_NOT_FOUND", ex.getErrorCode());
    }

    @Test
    void borrowBook_会员不存在_抛MEMBER_NOT_FOUND() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> library.borrowBook("B001", "M999"));

        assertEquals("MEMBER_NOT_FOUND", ex.getErrorCode());
    }

    @Test
    void returnBook_成功_书恢复可借() {
        library.borrowBook("B001", "M001");

        double fine = library.returnBook("B001", "M001");

        assertEquals(0.0, fine);
        assertTrue(findBook("B001").isAvailable());
        assertTrue(library.getActiveLoans().isEmpty());
    }

    @Test
    void saveBooks_应调用仓库保存() {
        library.saveBooks();

        verify(bookRepository).save(eq(booksFile), any());
    }

    private Book findBook(String id) {
        return library.listBooks().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }
}
