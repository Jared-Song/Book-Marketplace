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
@Table(name = "user_incentive")
public class UserIncentive {
    @Id
    @GeneratedValue(generator = "user_incentive_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "user_incentive_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "user_incentive_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", insertable=false, updatable=false)
    private User customer_id;

    @OneToOne
    @JoinColumn(name = "incentive_id", insertable=false, updatable=false)
    private Incentive seller_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(User customer_id) {
        this.customer_id = customer_id;
    }

    public Incentive getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(Incentive seller_id) {
        this.seller_id = seller_id;
    }
}
