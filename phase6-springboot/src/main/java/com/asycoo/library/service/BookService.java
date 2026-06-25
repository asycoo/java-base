package com.asycoo.library.service;

import com.asycoo.library.dto.BookCreateRequest;
import com.asycoo.library.entity.Book;
import com.asycoo.library.exception.BusinessException;
import com.asycoo.library.repository.BookRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 业务层 — 不处理 HTTP，只处理业务逻辑
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("BOOK_NOT_FOUND", "图书不存在: " + id));
    }

    public Book createBook(BookCreateRequest request) {
        if (bookRepository.existsById(request.id())) {
            throw new BusinessException("BOOK_DUPLICATE", "图书 ID 已存在: " + request.id());
        }
        Book book = new Book(request.id(), request.title(), request.author(), request.price(), true);
        return bookRepository.save(book);
    }

    public void deleteBook(String id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException("BOOK_NOT_FOUND", "图书不存在: " + id);
        }
        bookRepository.deleteById(id);
    }
}
