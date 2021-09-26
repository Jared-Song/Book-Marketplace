package com.rmit.sept.bk_loginservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "incentives")
public class Incentive {
    @Id
    @GeneratedValue(generator = "incentive_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "incentive_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "incentive_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
    @Column(name = "incentive_id")
    private Long id;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "seller_id")
    private User sellerId;

    @Column(name = "spending_amount_req")
    private int spendingAmountReq;

    @Column(name = "discount_amount")
    private int discountAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSellerId() {
        return sellerId;
    }

    public void setSellerId(User sellerId) {
        this.sellerId = sellerId;
    }

    public int getSpendingAmountReq() {
        return spendingAmountReq;
    }

    public void setSpendingAmountReq(int spendingAmountReq) {
        this.spendingAmountReq = spendingAmountReq;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

}
