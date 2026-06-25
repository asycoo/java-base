package com.asycoo.library.repository;

import com.asycoo.library.model.Book;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/**
 * 数据访问层 — 先用内存 Map，Week 8 再换 JPA
 */
@Repository
public class BookRepository {

    private final Map<String, Book> books = new ConcurrentHashMap<>();

    @PostConstruct
    void initSampleData() {
        save(new Book("B001", "Java核心技术", "Horstmann", 128.0, true));
        save(new Book("B002", "Effective Java", "Bloch", 89.0, true));
        save(new Book("B003", "深入理解Java虚拟机", "周志明", 99.0, true));
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }

    public void save(Book book) {
        books.put(book.id(), book);
    }

    public boolean deleteById(String id) {
        return books.remove(id) != null;
    }
}
