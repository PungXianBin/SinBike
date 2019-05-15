package com.example.sinbike.POJO;

public class FinePayment extends Payment {
    private String fineId;

    public FinePayment() {
    }

    public FinePayment(String fineid) {
        this.fineId = fineid;
    }

    public String getFineid() {
        return fineId;
    }

    public void setFineid(String fineid) {
        this.fineId = fineid;
    }
}
