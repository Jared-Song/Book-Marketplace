package com.rmit.sept.bk_loginservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "book_images")
public class BookImage {
    @Id
    @GeneratedValue(generator = "book_image_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "book_image_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "book_image_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
    @Column(name = "book_images_id")
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "url")
    private String url;

    @Column(name = "image_number")
    private int imageNumber;

    public BookImage(Long id, String url, int imageNumber) {
        this.id = id;
        this.url = url;
        this.imageNumber = imageNumber;
    }

    public BookImage() {
        
    }


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
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }
}
