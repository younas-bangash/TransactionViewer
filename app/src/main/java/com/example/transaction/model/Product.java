package com.example.transaction.model;


import android.content.Context;

import com.example.transaction.R;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class Product implements Serializable {
    public String name;
    public double price;
    public int numberOfTransactions;
    public List<Pair> priceHistory = new ArrayList<>();
    public double sum;

    public String getNumberOfTransactions(Context context) {
        return String.valueOf(numberOfTransactions) + " " + context.getResources().getString(R.string.transactions);
    }

    @Override
    public boolean equals(Object o) {
        if (((Product) o).name == this.name) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ByteBuffer.wrap(name.getBytes()).getInt();
    }

}
