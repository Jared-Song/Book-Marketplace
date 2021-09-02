package com.rmit.sept.bk_loginservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    @Email(message = "Email needs to be a valid email address")
    private String email;

    @NotBlank(message = "Username is required")
    @Column(unique = true, name = "username")
    private String username;

    @NotBlank(message = "Please enter your full name")
    @Column(name = "full_name")
    private String fullName;

    @NotBlank(message = "Password field is required")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Please enter your full address")
    @Column(name = "address")
    private String address;

    @Transient
    private String confirmPassword;
    private Date create_At;
    private Date update_At;

    @Column(name = "rating")
    private int rating;
    @Column(name = "rating_no")
    private int ratingNo;

    // OneToMany with Project

    public User(Long id, String username, String password, String email, String first_name, String middle_name,
            String last_name, int rating, int rating_no, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = first_name + " " + middle_name + " " + last_name;
        this.rating = rating;
        this.ratingNo = rating_no;
        this.address = address;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRating() {
        return rating;
    }

    public int getRatingNo() {
        return ratingNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    @PrePersist
    protected void onCreate() {
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date();
    }

    /*
     * UserDetails interface methods
     */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String name) { fullName = name; }
}