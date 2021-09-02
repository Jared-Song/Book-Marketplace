package com.rmit.sept.bk_loginservices.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;
    @Column(name = "book_title")
    private String title;
    @Column(name = "author_first_name")
    private String authorFirstName;
    @Column(name = "author_last_name")
    private String authorLastName;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User sellerId;
    @Column(name = "ISBN")
    private int isbn;
    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy="book")
    private List<BookImage> imageURL;

    @Column(name = "price")
    private double price;
    @Column(name = "rating")
    private int rating;
    @Column(name = "rating_no")
    private int rating_no;
    @Column(name = "service_id")
    private ServiceType serviceType;

    @Column(name = "release_date")
    private Date releaseDate;
    @Column(name = "posted_date")
    private Date postedDate;

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

    public User getSellerId() {
        return sellerId;
    }

    public void setSeller(User sellerId) {
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

    public int getRating_no() {
        return rating_no;
    }

    public void setRating_no(int rating_no) {
        this.rating_no = rating_no;
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

    public String getImageFront() {
        return imageURL.get(0).getUrl();
    }

    public void setImageFront(String imageURL) {
        this.imageURL.get(0).setUrl(imageURL);
    }

    public List<BookImage> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<BookImage> imageURL) {
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
        this.releaseDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.postedDate = new Date();
    }

}
