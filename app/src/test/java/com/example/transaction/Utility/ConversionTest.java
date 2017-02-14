package com.example.transaction.Utility;

import com.example.transaction.BuildConfig;
import com.example.transaction.activity.ProductHistoryActivity;
import com.example.transaction.model.Product;
import com.example.transaction.model.Rate;
import com.example.transaction.model.Transaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16, manifest = "src/main/AndroidManifest.xml")
public class ConversionTest {

    private ProductHistoryActivity mActivity;

    @Before
    public void setup() {
        mActivity = Robolectric.buildActivity(ProductHistoryActivity.class).create().get();
    }


    @Test
    public void checkCalculateFirstData() {
        try {
            List<Rate> rateList = Json.parseExchangeRates(mActivity.getBaseContext(), "first_rates.json");
            List<Transaction> transactionList = Json.parseTransactions(mActivity.getBaseContext(), "first_transactions.json");
            Map<String, Product> convert = Conversion.convert(rateList, transactionList);
            assertThat(convert, is(notNullValue()));
            assertThat(convert.size(), is(12));
            assertThat(convert.get("A0911"), is(notNullValue()));
            assertThat(convert.get("X1893"), is(notNullValue()));
            assertThat(convert.get("W9806"), is(notNullValue()));
            assertThat(convert.get("C7156"), is(notNullValue()));
            assertThat(convert.get("N6308"), is(notNullValue()));
            assertThat(convert.get("R9704"), is(notNullValue()));
            assertThat(convert.get("V5239"), is(notNullValue()));
            assertThat(convert.get("G7340"), is(notNullValue()));
            assertThat(convert.get("A8964"), is(notNullValue()));
            assertThat(convert.get("M3474"), is(notNullValue()));
            assertThat(convert.get("J4064"), is(notNullValue()));
            assertThat(convert.get("O7730"), is(notNullValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkCalculateSecondData() {
        try {
            List<Rate> rateList = Json.parseExchangeRates(mActivity.getBaseContext(), "second_rates.json");
            List<Transaction> transactionList = Json.parseTransactions(mActivity.getBaseContext(), "second_transactions.json");
            Map<String, Product> convert = Conversion.convert(rateList, transactionList);

            assertThat(convert, is(notNullValue()));
            assertThat(convert.size(), is(1));
            assertThat(convert.get("J4064"), is(notNullValue()));
            assertThat(convert.get("J4064").numberOfTransactions, is(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}