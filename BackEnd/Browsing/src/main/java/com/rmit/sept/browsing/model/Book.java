package com.rmit.sept.browsing.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@TypeDef(
    name = "pg_enum",
    typeClass = PostgreSQLEnumType.class
)
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
    
    @Column(name = "author_name")
    private String authorName;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @Column(name = "ISBN")
    private int isbn;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "category")
    private String category;

    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "quality", columnDefinition = "quality")
    @Type(type = "pg_enum")
    private Quality quality;

    @OneToMany(mappedBy="book")
    @JsonManagedReference
    private List<BookImage> imageURL;
    
    @Column(name = "price")
    private double price;

    @Column(name = "rating_total")
    private int ratingTotal;

    @Column(name = "rating_no")
    private int rating_no;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "service_id", columnDefinition = "service_type")
    @Type(type = "pg_enum")
    private ServiceType serviceType;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "status_id", columnDefinition = "book_status")
    @Type(type = "pg_enum")
    private BookStatus bookStatus;

    public static final int INITIAL_RATING = 0;
    public static final int INITIAL_NUM_RATINGS = 0;

    @Column(name = "create_at")
    private Date created_At;

    @Column(name = "update_at")
    private Date updated_At;

    @Transient
    private Long sellerId;

    @Transient
    private Long requestId;

    public Book(String title, String authorname, User seller, int isbn, int quantity, String category, Quality quality, List<BookImage> imageURL, double price, ServiceType serviceType, BookStatus bookStatus) {
        this.title = title;
        this.authorName = authorname;
        this.seller = seller;
        this.isbn = isbn;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
        this.imageURL = imageURL;
        this.price = price;
        this.serviceType = serviceType;
        this.bookStatus = bookStatus;
    }

    public Book(Long id, String title, String authorname, User seller, int isbn, int quantity, String category, Quality quality, List<BookImage> imageURL, double price, ServiceType serviceType, BookStatus bookStatus) {
        this.id = id;
        this.title = title;
        this.authorName = authorname;
        this.seller = seller;
        this.isbn = isbn;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
        this.imageURL = imageURL;
        this.price = price;
        this.serviceType = serviceType;
        this.bookStatus = bookStatus;
    }

    public Book(){
        
    }

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

    public int getRatingNo() {
        return rating_no;
    }

    public void setRatingNo(int rating_no) {
        this.rating_no = rating_no;
    }

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

    public int getRatingTotal() {
        return ratingTotal;
    }

    public void setRatingTotal(int ratingTotal) {
        this.ratingTotal = ratingTotal;
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

    public Date getcreated_At() {
        return created_At;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }

}
