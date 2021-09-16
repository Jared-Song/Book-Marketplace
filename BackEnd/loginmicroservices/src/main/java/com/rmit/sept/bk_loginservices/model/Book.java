package com.rmit.sept.bk_loginservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(generator = "book_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "book_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "book_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
    @Column(name = "book_id")
    private long id;
    @Column(name = "book_title")
    private String title;
    @Column(name = "author_full_name")
    private String authorName;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User sellerId;
    @Column(name = "ISBN")
    private String category;    
    private int isbn;
    @Column(name = "quantity")
    private int quantity;
    private int ratings;

    @Enumerated(EnumType.STRING)
    private Quality quality;

    private Date created_At;
    private Date updated_At;

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Date getcreated_At() {
        return created_At;
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
