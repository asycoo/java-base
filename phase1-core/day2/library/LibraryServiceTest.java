package day2.library;

import java.time.LocalDate;

/**
 * LibraryService 自测（非交互）
 */
public class LibraryServiceTest {

    public static void main(String[] args) {
        LibraryService library = new LibraryService();
        library.addBook(new Book("B001", "Java 核心技术", "Horstmann", 128.0));
        library.registerMember(new Member("M001", "张三"));

        // 借书
        LoanRecord loan = library.borrowBook("B001", "M001");
        assert !library.listBooks().get(0).isAvailable() : "借出后应不可借";
        assert library.getActiveLoans().size() == 1 : "应有 1 条在借记录";

        // 还书（未逾期）
        double fine = library.returnBook("B001", "M001");
        assert fine == 0 : "未逾期罚款应为 0，实际 " + fine;
        assert library.listBooks().get(0).isAvailable() : "归还后应可借";
        assert library.getActiveLoans().isEmpty() : "在借记录应为空";

        // 异常：借不存在的书
        try {
            library.borrowBook("B999", "M001");
            throw new AssertionError("应抛 LibraryException");
        } catch (LibraryException ignored) {
        }

        // 异常：还不存在的借阅
        try {
            library.returnBook("B001", "M001");
            throw new AssertionError("应抛 LibraryException");
        } catch (LibraryException ignored) {
        }

        System.out.println("LibraryServiceTest: 全部通过 ✓");
    }
}
