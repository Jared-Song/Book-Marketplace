package com.rmit.sept.bk_loginservices.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@TypeDef(
    name = "pg_enum",
    typeClass = PostgreSQLEnumType.class
)
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "user_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "user_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
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
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role", columnDefinition = "role")
    @Type(type = "pg_enum")
    private Role role;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "status_id", columnDefinition = "user_status")
    @Type(type = "pg_enum")
    private UserStatus status;
    
    @Column(name = "rating_total")
    private int ratingTotal;
    @Column(name = "rating_no")
    private int ratingNo;
    
    public static final int INITIAL_RATING = 0;
    public static final int INITIAL_NUM_RATINGS = 0;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private Business business;

    public User(String email, String username, String fullname, String password, String address) {
        this.email = email;
        this.username = username;
        this.fullName = fullname;
        this.password = password;
        this.address = address;
        this.role = Role.USER_NORMAL;
        this.status = UserStatus.ENABLED;
        this.ratingTotal = INITIAL_RATING;
        this.ratingNo = INITIAL_NUM_RATINGS;
        this.business = null;
    }

    public Business getBusiness(){
        return business;
    }

    public void setBusiness(Business business){
        this.business = business;
    }


    private Date create_At;
    private Date update_At;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getUserStatus() {
        return status;
    }

    public void setUserStatus(UserStatus status) {
        this.status = status;
    }

    public int getRating() {
        return ratingTotal;
    }

    public void setRating(int rating) {
        this.ratingTotal = rating;
    }

    public int getRatingNo() {
        return ratingNo;
    }

    public void setRatingNo(int ratingNo) {
        this.ratingNo = ratingNo;
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