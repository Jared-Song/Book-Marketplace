package com.rmit.sept.browsing.model;

import java.util.List;

public class BookForm {
    private Long id;
    private User seller;
    private Long sellerId;
    private String title;
    private String authorName;
    private double price;
    private String category;
    private int isbn;
    private int quantity;
    private List<BookImage> imageURL;
    private Quality quality;
    private BookStatus bookStatus;
    private int ratingTotal;
    private int ratingNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
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

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public double getRatingTotal() {
        return ratingTotal;
    }

    public void setRatingTotal(int ratingTotal) {
        this.ratingTotal = ratingTotal;
    }

    public int getRatingNo() {
        return ratingNo;
    }

    public void setRatingNo(int ratingNo) {
        this.ratingNo = ratingNo;
    }

}
