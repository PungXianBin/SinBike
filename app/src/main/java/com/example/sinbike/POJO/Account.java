package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;

public class Account extends Model {

    private String name;
    private String email;
    private String telephoneNumber;
    private String gender;
    private String dateOfBirth;
    private int status;
    private double accountBalance;

    public Account() {
    }

    public Account(String name, String email, String telephoneNumber, String gender, String dateOfBirth, int status, double accountBalance) {
        this.name = name;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.accountBalance = accountBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth + '\'' +
                ", status=" + status +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
