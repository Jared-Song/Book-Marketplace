package com.rmit.sept.books.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(generator = "request_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "request_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "request_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
    @Column(name = "request_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "request")
    @JsonManagedReference
    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "request")
    private String request;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "request_type", columnDefinition = "request_type")
    @Type(type = "pg_enum")
    private RequestType requestType;

    @Column(name = "create_at")
    private Date created_At;
    @Column(name = "update_at")
    private Date updated_At;

    @Transient
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Date getcreated_At() {
        return created_At;
    }

    public void setcreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getupdated_At() {
        return updated_At;
    }

    public void setupdated_At(Date updated_At) {
        this.updated_At = updated_At;
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
