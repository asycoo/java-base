package day2.library;

/**
 * 图书
 */
public class Book {

    private final String id;
    private final String title;
    private final String author;
    private final double price;
    private boolean available;

    public Book(String id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.available = true;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%.2f) %s",
                id, title, author, price, available ? "可借" : "已借出");
    }
}
