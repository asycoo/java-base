package day3.library;

import java.nio.file.Path;
import java.util.List;
import phase4.exception.BusinessException;

/** Day3 图书系统 v3 自动化验证（JSON + 统一异常） */
public class LibraryV3Test {

    private static final Path BOOKS_FILE = LibraryMain.locateBooksFile();

    public static void main(String[] args) {
        BookRepository repo = new BookRepository();
        LibraryService library = new LibraryService(repo, BOOKS_FILE);

        List<Book> books = library.listBooks();
        check(books.size() == 3, "应加载 3 本书，实际 " + books.size());

        List<Book> sorted = library.listBooksSortedByPrice();
        check(sorted.get(0).getPrice() <= sorted.get(1).getPrice(), "价格应升序");

        List<Book> byBloch = library.findByAuthor("Bloch");
        check(byBloch.size() == 1, "Bloch 应有 1 本，实际 " + byBloch.size());

        List<Book> byBlochLower = library.findByAuthor("bloch");
        check(byBlochLower.size() == 1, "bloch 忽略大小写应有 1 本，实际 " + byBlochLower.size());

        library.registerMember(new Member("M001", "张三"));
        library.borrowBook("B001", "M001");
        check(!library.listBooks().stream().filter(b -> b.getId().equals("B001")).findFirst().get().isAvailable(),
                "借出后 B001 应不可借");

        try {
            library.borrowBook("B001", "M001");
            throw new AssertionError("应抛 BusinessException");
        } catch (BusinessException e) {
            check("BOOK_NOT_AVAILABLE".equals(e.getErrorCode()), "错误码应为 BOOK_NOT_AVAILABLE");
        }

        library.saveBooks();
        library.returnBook("B001", "M001");
        library.saveBooks();

        System.out.println("LibraryV3Test: 全部通过 ✓");
    }

    private static void check(boolean ok, String msg) {
        if (!ok) throw new AssertionError(msg);
    }
}
