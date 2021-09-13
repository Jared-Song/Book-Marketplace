package com.rmit.sept.bk_loginservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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
    private User userId;

    @Column(name = "request")
    private String request;
    @Column(name = "request_type")
    private RequestType requestType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
}
