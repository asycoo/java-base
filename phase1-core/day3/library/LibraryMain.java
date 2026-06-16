package day3.library;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * 图书管理系统 v2 - 控制台入口
 *
 * 菜单：
 *   1. 列出图书（按价格排序）
 *   2. 按作者搜索
 *   3. 添加图书
 *   4. 借书
 *   5. 还书
 *   6. 查看在借记录
 *   0. 退出（自动保存）
 */
public class LibraryMain {

    /** 自动定位 books.txt（兼容从 java-base 或 phase1-core 目录运行） */
    private static final Path BOOKS_FILE = locateBooksFile();

    static Path locateBooksFile() {
        Path[] candidates = {
                Path.of("day3/library/books.txt"),
                Path.of("phase1-core/day3/library/books.txt")
        };
        for (Path path : candidates) {
            if (Files.exists(path)) {
                return path;
            }
        }
        // 默认写入源码目录（phase1-core 下运行时）
        return Path.of("day3/library/books.txt");
    }

    public static void main(String[] args) {
        BookRepository repository = new BookRepository();
        LibraryService library = new LibraryService(repository, BOOKS_FILE);

        library.registerMember(new Member("M001", "张三"));
        library.registerMember(new Member("M002", "李四"));

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== 图书管理系统 v2（持久化） ===");

        while (true) {
            printMenu();
            System.out.print("请选择: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> listBooksByPrice(library);
                    case "2" -> searchByAuthor(library, scanner);
                    case "3" -> addBook(library, scanner);
                    case "4" -> borrowBook(library, scanner);
                    case "5" -> returnBook(library, scanner);
                    case "6" -> listActiveLoans(library);
                    case "0" -> {
                        library.saveBooks();
                        System.out.println("已保存，再见！");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("无效选项");
                }
            } catch (LibraryException e) {
                System.out.println("操作失败：" + e.getMessage());
            } catch (Exception e) {
                System.out.println("系统错误：" + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. 列出图书（按价格排序）");
        System.out.println("2. 按作者搜索");
        System.out.println("3. 添加图书");
        System.out.println("4. 借书");
        System.out.println("5. 还书");
        System.out.println("6. 查看在借记录");
        System.out.println("0. 退出（保存）");
    }

    private static void listBooksByPrice(LibraryService library) {
        for (Book book : library.listBooksSortedByPrice()) {
            System.out.println(book);
        }
    }

    private static void searchByAuthor(LibraryService library, Scanner scanner) {
        System.out.print("作者名: ");
        String author = scanner.nextLine().trim();
        List<Book> found = library.findByAuthor(author);
        if (found.isEmpty()) {
            System.out.println("未找到该作者的图书");
        } else {
            found.forEach(System.out::println);
        }
    }

    private static void addBook(LibraryService library, Scanner scanner) {
        System.out.print("图书 ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("书名: ");
        String title = scanner.nextLine().trim();
        System.out.print("作者: ");
        String author = scanner.nextLine().trim();
        System.out.print("价格: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        if (title.isEmpty() || author.isEmpty()) {
            throw new LibraryException("书名和作者不能为空");
        }
        library.addBook(new Book(id, title, author, price));
        library.saveBooks();
        System.out.println("添加成功并已保存");
    }

    private static void borrowBook(LibraryService library, Scanner scanner) {
        System.out.print("图书 ID: ");
        String bookId = scanner.nextLine().trim();
        System.out.print("会员 ID: ");
        String memberId = scanner.nextLine().trim();
        LoanRecord record = library.borrowBook(bookId, memberId);
        library.saveBooks();
        System.out.println("借书成功，应还日期：" + record.getDueDate());
    }

    private static void returnBook(LibraryService library, Scanner scanner) {
        System.out.print("图书 ID: ");
        String bookId = scanner.nextLine().trim();
        System.out.print("会员 ID: ");
        String memberId = scanner.nextLine().trim();
        double fine = library.returnBook(bookId, memberId);
        library.saveBooks();
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
