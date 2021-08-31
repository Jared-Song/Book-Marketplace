package com.rmit.sept.bk_loginservices.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private Long sellerId;
    private int isbn;
    private int quantity;
    private String imageURL;
    private double price;
    private int rating;
    private ServiceType serviceType;

    private Date createdAt;
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSeller(Long sellerId) {
        this.sellerId = sellerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public void addQuantity(int quantity) {
        this.quantity = this.quantity + quantity;
    }

    public void removeQuantity(int quantity) {
        if (quantity > this.quantity) {
            this.quantity = 0;
        } else {
            this.quantity = this.quantity - quantity;
        }
    }

    // public void addRating(Rating rating) {
    // ratings.add(rating);
    // }

    // public double getRating() {
    // double sum = 0;
    // for (Rating rating : ratings) {
    // sum += rating.getRating();
    // }
    // return sum/ratings.size();
    // }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getRatings() {
        return rating;
    }

    public void setRatings(int ratings) {
        this.rating = ratings;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
