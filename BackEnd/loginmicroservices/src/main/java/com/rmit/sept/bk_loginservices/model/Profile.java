package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "profiles")
public class Profile implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_id")
    private Role role_id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "status_id")
    private Status status_id;

    public Role getRole_id() {
        return role_id;
    }

    public void setRole_id(Role role_id) {
        this.role_id = role_id;
    }

    public Status getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Status status_id) {
        this.status_id = status_id;
    }
}
