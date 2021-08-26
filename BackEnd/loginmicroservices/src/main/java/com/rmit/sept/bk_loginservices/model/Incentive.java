package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

@Entity
@Table(name = "incentive_ids")
public class Incentive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reviewerId;
    private Long sellerId;
    private int spendingAmountReq;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
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

    private int discountAmount;
}
