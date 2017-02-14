package com.example.transaction.Utility;

import android.content.Context;

import com.example.transaction.model.Rate;
import com.example.transaction.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Json {
    private static final ObjectMapper mapper = new ObjectMapperFactory().getObjectMapper();

    public static List<Rate> parseExchangeRates(Context context, String fileName) throws IOException {
        InputStream exchangeStream = context.getAssets().open(fileName);
        TypeReference<List<Rate>> exchangeRateTypeRef = new TypeReference<List<Rate>>() {
        };

        return mapper.readValue(exchangeStream, exchangeRateTypeRef);
    }


    public static List<Transaction> parseTransactions(Context context, String fileName) throws IOException {
        InputStream transactionStream = context.getAssets().open(fileName);
        TypeReference<List<Transaction>> transactionsTypeRef = new TypeReference<List<Transaction>>() {
        };

        return mapper.readValue(transactionStream, transactionsTypeRef);
    }

}