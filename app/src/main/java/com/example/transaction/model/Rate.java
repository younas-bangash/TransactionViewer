package com.example.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DecimalFormat;

public class Rate {

    @JsonProperty("from")
    private String from;
    @JsonProperty("rate")
    private Double rate;
    @JsonProperty("to")
    private String to;


    public String getFrom() {
        return from;
    }

    public double getRate() {
        return roundTwoDecimals(rate);
    }

    public String getTo() {
        return to;
    }

    private static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
