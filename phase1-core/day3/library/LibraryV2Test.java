package day3.library;

import java.nio.file.Path;
import java.util.List;

/** Day3 图书系统 v2 自动化验证 */
public class LibraryV2Test {

    private static final Path BOOKS_FILE = LibraryMain.locateBooksFile();

    public static void main(String[] args) {
        BookRepository repo = new BookRepository();
        LibraryService library = new LibraryService(repo, BOOKS_FILE);

        // 1. 启动应加载 books.txt 中的 3 本书
        List<Book> books = library.listBooks();
        check(books.size() == 3, "应加载 3 本书，实际 " + books.size());

        // 2. 按价格排序
        List<Book> sorted = library.listBooksSortedByPrice();
        check(sorted.get(0).getPrice() <= sorted.get(1).getPrice(), "价格应升序");

        // 3. 按作者搜索（大小写）
        List<Book> byBloch = library.findByAuthor("Bloch");
        check(byBloch.size() == 1, "Bloch 应有 1 本，实际 " + byBloch.size());

        List<Book> byBlochLower = library.findByAuthor("bloch");
        check(byBlochLower.size() == 1, "bloch 忽略大小写应有 1 本，实际 " + byBlochLower.size());

        // 4. 借还书 + 保存
        library.registerMember(new Member("M001", "张三"));
        library.borrowBook("B001", "M001");
        check(!library.listBooks().stream().filter(b -> b.getId().equals("B001")).findFirst().get().isAvailable(),
                "借出后 B001 应不可借");

        library.saveBooks();
        library.returnBook("B001", "M001");
        library.saveBooks();

        System.out.println("LibraryV2Test: 全部通过 ✓");
    }

    private static void check(boolean ok, String msg) {
        if (!ok) throw new AssertionError(msg);
    }
}
