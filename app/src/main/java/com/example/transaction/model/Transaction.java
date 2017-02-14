package com.example.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

    @JsonProperty("amount")
    private String amount;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("currency")
    private String currency;


    public double getAmount() {
        return Double.parseDouble(amount);
    }

    public String getCurrency() {
        return currency;
    }

    public String getSku() {
        return sku;
    }
}

