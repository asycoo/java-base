package day2.library;

import java.util.Scanner;

/**
 * 图书管理系统 v1 - 控制台入口
 *
 * 菜单：
 *   1. 列出图书
 *   2. 借书
 *   3. 还书
 *   4. 查看在借记录
 *   0. 退出
 *
 * main 只负责菜单和 I/O，业务调用 LibraryService
 */
public class LibraryMain {

    public static void main(String[] args) {
        LibraryService library = createSampleLibrary();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 图书管理系统 v1 ===");

        while (true) {
            printMenu();
            System.out.print("请选择: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> listBooks(library);
                    case "2" -> borrowBook(library, scanner);
                    case "3" -> returnBook(library, scanner);
                    case "4" -> listActiveLoans(library);
                    case "0" -> {
                        System.out.println("再见！");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("无效选项");
                }
            } catch (LibraryException e) {
                System.out.println("操作失败：" + e.getMessage());
            }
        }
    }

    private static LibraryService createSampleLibrary() {
        LibraryService library = new LibraryService();
        library.addBook(new Book("B001", "Java 核心技术", "Horstmann", 128.0));
        library.addBook(new Book("B002", "Effective Java", "Bloch", 89.0));
        library.registerMember(new Member("M001", "张三"));
        library.registerMember(new Member("M002", "李四"));
        return library;
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. 列出图书");
        System.out.println("2. 借书");
        System.out.println("3. 还书");
        System.out.println("4. 查看在借记录");
        System.out.println("0. 退出");
    }

    private static void listBooks(LibraryService library) {
        for (Book book : library.listBooks()) {
            System.out.println(book);
        }
    }

    private static void borrowBook(LibraryService library, Scanner scanner) {
        System.out.print("图书 ID: ");
        String bookId = scanner.nextLine().trim();
        System.out.print("会员 ID: ");
        String memberId = scanner.nextLine().trim();
        LoanRecord record = library.borrowBook(bookId, memberId);
        System.out.println("借书成功，应还日期：" + record.getDueDate());
    }

    private static void returnBook(LibraryService library, Scanner scanner) {
        System.out.print("图书 ID: ");
        String bookId = scanner.nextLine().trim();
        System.out.print("会员 ID: ");
        String memberId = scanner.nextLine().trim();
        double fine = library.returnBook(bookId, memberId);
        if (fine > 0) {
            System.out.printf("还书成功，逾期罚款：%.2f 元%n", fine);
        } else {
            System.out.println("还书成功，无逾期");
        }
    }

    private static void listActiveLoans(LibraryService library) {
        for (LoanRecord loan : library.getActiveLoans()) {
            System.out.printf("书=%s 会员=%s 借出=%s 应还=%s%n",
                    loan.getBookId(), loan.getMemberId(),
                    loan.getLoanDate(), loan.getDueDate());
        }
    }
}
