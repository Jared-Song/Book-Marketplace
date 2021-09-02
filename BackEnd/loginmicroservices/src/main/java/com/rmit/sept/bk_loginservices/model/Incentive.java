package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

@Entity
@Table(name = "incentives")
public class Incentive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "incentive_id")
    private Long id;

    @OneToOne
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
