package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

@Entity
@Table(name = "book_images")
public class BookImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_images_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "url")
    private String url;

    @Column(name = "image_number")
    private int ImageNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageNumber() {
        return ImageNumber;
    }

    public void setImageNumber(int imageNumber) {
        ImageNumber = imageNumber;
    }
}
