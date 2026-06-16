package day3.library;

/**
 * 图书 — 支持 CSV 序列化
 */
public class Book {

    private final String id;
    private final String title;
    private final String author;
    private final double price;
    private boolean available;

    public Book(String id, String title, String author, double price) {
        this(id, title, author, price, true);
    }

    public Book(String id, String title, String author, double price, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.available = available;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    /** CSV 一行，不含表头 */
    public String toCsvLine() {
        return String.join(",",
                id, title, author, String.valueOf(price), String.valueOf(available));
    }

    /** 解析 CSV 一行，如 B001,Java核心技术,Horstmann,128.0,true */
    public static Book fromCsvLine(String line) {
        String[] parts = line.split(",", 5);
        if (parts.length != 5) {
            throw new IllegalArgumentException("CSV 格式错误: " + line);
        }
        return new Book(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                Double.parseDouble(parts[3].trim()),
                Boolean.parseBoolean(parts[4].trim())
        );
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%.2f) %s",
                id, title, author, price, available ? "可借" : "已借出");
    }
}
