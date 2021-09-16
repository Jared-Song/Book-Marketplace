package com.rmit.sept.bk_loginservices.model;

import java.util.List;
import com.rmit.sept.bk_loginservices.model.BookImage;

public class BookForm {
    private Long id;
    private User sellerId;
    private String title;
    private String authorName;
    private double price;
    private String category;
    private int isbn;
    private int quantity;
    private List<BookImage> imageURL;
    private Quality quality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSellerId() {
        return sellerId;
    }

    public void setSellerId(User sellerId) {
        this.sellerId = sellerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getISBN() {
        return isbn;
    }

    public void setISBN(int isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<BookImage> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<BookImage> imageURL) {
        this.imageURL = imageURL;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }
    
}
