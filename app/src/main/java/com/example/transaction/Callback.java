package com.example.transaction;

import com.example.transaction.model.Product;

import java.util.Map;

public interface Callback {
    void getTransactions(Map<String, Product> transactionMap);
}
