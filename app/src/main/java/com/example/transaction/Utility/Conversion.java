package com.example.transaction.Utility;

import com.example.transaction.model.Pair;
import com.example.transaction.model.Product;
import com.example.transaction.model.Rate;
import com.example.transaction.model.Transaction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conversion {
    public static String GBP = "GBP";
    public static Map<String, Double> ratesPopulated;

    public static Map<String, Product> convert(List<Rate> rates, List<Transaction> transactions) {
        ratesPopulated = convertCurrencyToGbp(rates);

        Map<String, Product> productMap = new HashMap<>();

        Product product;
        Pair pair;
        for (Transaction transaction : transactions) {
            product = new Product();
            pair = new Pair();
            if (transaction.getCurrency() == null)
                throw new IllegalArgumentException("Currency cannot be null");
            if (transaction.getSku() == null)
                throw new IllegalArgumentException("Name of rates cannot be null");

            product.name = transaction.getSku();
            product.price = getPriceGbp(transaction.getCurrency(), transaction.getAmount());

            pair.oldCurrency = transaction.getCurrency().toString() + " " + transaction.getAmount();
            pair.priceGbp = product.price;

            if (productMap.containsKey(product.name)) {
                product.priceHistory = productMap.get(product.name).priceHistory;
            }

            product.priceHistory.add(pair);

            product.sum = getSum(product.priceHistory);
            product.numberOfTransactions = product.priceHistory.size();
            productMap.put(product.name, product);
            product = null;
            pair = null;

        }

        ratesPopulated = null;
        System.gc();
        return productMap;
    }

    private static double getSum(List<Pair> pairList) {
        double sum = 0;
        for (Pair pair : pairList) {
            sum = sum + pair.priceGbp;
        }
        return roundTwoDecimals(sum);
    }

    private static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.###");
        return Double.valueOf(twoDForm.format(d));
    }

    private static double getPriceGbp(String rate, double price) {
        return roundTwoDecimals(ratesPopulated.get(rate) * price);
    }

    public static Map<String, Double> convertCurrencyToGbp(List<Rate> rates) {
        Map<String, Double> rateSet = new HashMap<>();
        List<Rate> lRate = new ArrayList<>(rates);
        for (int i = 0; i < lRate.size(); i++) {
            if (lRate.get(i).getTo().equalsIgnoreCase(GBP)) {
                rateSet.put(lRate.get(i).getFrom(), lRate.get(i).getRate());
                lRate.remove(i);
                i = -1;
            } else if (rateSet.containsKey(lRate.get(i).getTo())) {
                rateSet.put(lRate.get(i).getFrom(), rateSet.get(lRate.get(i).getTo()) * lRate.get(i).getRate());
                lRate.remove(i);
                i = -1;
            }

        }
        return rateSet;
    }

}
