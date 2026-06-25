package com.asycoo.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    private double price;
    private boolean available;

    protected Book() {
    }

    public Book(String id, String title, String author, double price, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
