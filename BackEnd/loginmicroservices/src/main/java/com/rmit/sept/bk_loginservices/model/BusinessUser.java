package com.rmit.sept.bk_loginservices.model;

public class BusinessUser extends User {
    private int ABN;
    private String companyName;

    public BusinessUser(Long id, String username, String password, String email, String first_name, String middle_name, String last_name, int rating, int rating_no) {
        super(id, username, password, email, first_name, middle_name, last_name, rating, rating_no, last_name);



    }

    public int getABN() {
        return ABN;
    }

    public void setABN(int ABN) {
        this.ABN = ABN;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
