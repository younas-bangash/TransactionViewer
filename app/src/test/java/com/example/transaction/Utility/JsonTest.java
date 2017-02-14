package com.example.transaction.Utility;

import com.example.transaction.BuildConfig;
import com.example.transaction.activity.ProductHistoryActivity;
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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16, manifest = "src/main/AndroidManifest.xml")
public class JsonTest {

    private String checkFirstRatesWithErrorValues = "[{\"from\":,\"rate\":\"0.77\",\"to\":\"GBP\"}," +
            "{\"from\":\"GBP\",\"rate\":\"1.3\",\"to\":\"USD\"},{\"from\":\"USD\",\"rate\":\"1.09\"," +
            "\"to\":\"CAD\"},{\"from\":\"CAD\",\"rate\":\"0.92\",\"to\":\"USD\"},{\"from\":\"GBP\"," +
            "\"rate\":\"0.83\",\"to\":\"AUD\"},{\"from\":\"AUD\",\"rate\":\"1.2\",\"to\":\"GBP\"}]\n";
    private String checkFirstRatesWithNumberValues = "[{\"from\":\"USD\",\"rate\":0.77,\"to\":\"GBP\"}," +
            "{\"from\":\"GBP\",\"rate\":1.3,\"to\":\"USD\"},{\"from\":\"USD\",\"rate\":1.09,\"to\":\"CAD\"}," +
            "{\"from\":\"CAD\",\"rate\":0.92,\"to\":\"USD\"},{\"from\":\"GBP\",\"rate\":0.83,\"to\":" +
            "\"AUD\"},{\"from\":\"AUD\",\"rate\":1.2,\"to\":\"GBP\"}]\n";

    private String checkFirstRatesOneItem = "[{\"from\":\"USD\",\"rate\":0.77,\"to\":\"GBP\"}]";
    private String checkFirstRatesOneItemNonArray = "{\"from\":\"USD\",\"rate\":0.77,\"to\":\"GBP\"}";

    private String checkSecondTransactionWithErrorValues = "[{\"amount\":\"1\",\"sku\":,\"currency\"" +
            ":\"GBP\"},{\"amount\":\"1\",\"sku\":\"J4064\",\"currency\":\"EUR\"},{\"amount\":\"1\"," +
            "\"sku\":\"J4064\",\"currency\":\"USD\"},{\"amount\":\"1\",\"sku\":\"J4064\",\"currency\":\"AUD\"}]";
    private String checkSecondTransactionWithNumberValues = "[{\"amount\":1,\"sku\":\"J4064\"," +
            "\"currency\":\"GBP\"},{\"amount\":1,\"sku\":\"J4064\",\"currency\":\"EUR\"}," +
            "{\"amount\":1,\"sku\":\"J4064\",\"currency\":\"USD\"},{\"amount\":1,\"sku\":\"J4064\",\"currency\":\"AUD\"}]";

    private String checkSecondTransactionOneItem = "[{\"amount\":\"1\",\"sku\":\"J4064\",\"currency\":\"GBP\"}]";
    private String checkSecondTransactionOneItemNonArray = "{\"amount\":\"1\",\"sku\":\"J4064\",\"currency\":\"GBP\"}";

    private String errorJsonTransaction = "[{\"amount\":1,]";
    private String errorJsonRates = "[{\"from\":\"USD\",]";

    private String emptyJson = "";


    private ProductHistoryActivity mActivity;

    @Before
    public void setup() {
        mActivity = Robolectric.buildActivity(ProductHistoryActivity.class).create().get();
    }

    @Test
    public void checkErrorJson() {
        try {
            List<Rate> rate = Json.parseExchangeRates(errorJsonRates);
            assertThat(rate, is(nullValue()));

            //Since I don't know if we should always expect an array, I will consider any non array json String as a wrong one.
            rate = Json.parseExchangeRates(checkFirstRatesOneItemNonArray);
            assertThat(rate, is(nullValue()));

            List<Transaction> transactions = Json.parseTransactions(errorJsonTransaction);
            assertThat(transactions, is(nullValue()));

            //Since I don't know if we should always expect an array, I will consider any non array json String as a wrong one.
            transactions = Json.parseTransactions(checkSecondTransactionOneItemNonArray);
            assertThat(transactions, is(nullValue()));
        } catch (IOException e) {

        } catch (Exception e) {

        }
    }

    @Test
    public void checkEmptyJson() {
        try {
            List<Rate> rate = Json.parseExchangeRates(emptyJson);
            assertThat(rate, is(nullValue()));

            List<Transaction> transactions = Json.parseTransactions(emptyJson);
            assertThat(transactions, is(nullValue()));
        } catch (IOException e) {

        } catch (Exception e) {

        }
    }

    @Test
    public void checkFirstRates() {
        try {
            List<Rate> rateList = Json.parseExchangeRates(mActivity.getBaseContext(), "first_rates.json");
            assertThat(rateList, is(notNullValue()));
            assertThat(rateList.size(), is(6));
            assertThat(rateList.get(0).getFrom(), is("USD"));
            assertThat(rateList.get(0).getTo(), is("GBP"));

            Map<String, Double> stuff = Conversion.convertCurrencyToGbp(rateList);
            assertThat(stuff.containsKey("USD"), is(true));
            assertThat(stuff.containsKey("GBP"), is(true));
            assertThat(stuff.containsKey("AUD"), is(true));
            assertThat(stuff.containsKey("CAD"), is(true));

            assertThat(stuff.size(), is(4));

            assertThat(stuff.get("USD"), is(0.7721560000000001d));
            assertThat(stuff.get("GBP"), is(0.9959999999999999d));
            assertThat(stuff.get("AUD"), is(1.2d));
            assertThat(stuff.get("CAD"), is(0.7084d));

            rateList = Json.parseExchangeRates(checkFirstRatesOneItem);
            assertThat(rateList, is(notNullValue()));
            assertThat(rateList.size(), is(1));
            assertThat(rateList.get(0).getFrom(), is("USD"));
        } catch (IOException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkFirstRatesErrorValues() {
        try {
            List<Rate> rate = Json.parseExchangeRates(checkFirstRatesWithErrorValues);
            assertThat(rate, is(nullValue()));
        } catch (IOException e) {

        } catch (Exception e) {

        }
    }

    @Test
    public void checkFirstRatesWithNumbers() {
        try {
            List<Rate> rate = Json.parseExchangeRates(checkFirstRatesWithNumberValues);
            assertThat(rate, is(notNullValue()));
        } catch (IOException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkFirstTransactions() {
        try {
            List<Transaction> transactionList = Json.parseTransactions(mActivity.getBaseContext(), "first_transactions.json");
            assertThat(transactionList, is(notNullValue()));
            assertThat(transactionList.size(), is(5187));
        } catch (IOException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void checkSecondRates() {
        try {
            List<Rate> rateList = Json.parseExchangeRates(mActivity, "second_rates.json");
            assertThat(rateList, is(notNullValue()));
            assertThat(rateList.size(), is(6));

            Map<String, Double> stuff = Conversion.convertCurrencyToGbp(rateList);
            assertThat(stuff.containsKey("USD"), is(true));
            assertThat(stuff.containsKey("GBP"), is(true));
            assertThat(stuff.containsKey("AUD"), is(true));
            assertThat(stuff.containsKey("EUR"), is(true));

            assertThat(stuff.size(), is(4));

            assertThat(stuff.get("EUR"), is(0.5d));
            assertThat(stuff.get("GBP"), is(1.0d));
            assertThat(stuff.get("AUD"), is(0.125d));
            assertThat(stuff.get("USD"), is(0.25d));

        } catch (IOException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkSecondTransactions() {
        try {
            List<Transaction> transactionList = Json.parseTransactions(mActivity, "second_transactions.json");
            assertThat(transactionList, is(notNullValue()));
            assertThat(transactionList.size(), is(4));

            transactionList = Json.parseTransactions(checkSecondTransactionOneItem);
            assertThat(transactionList, is(notNullValue()));
            assertThat(transactionList.size(), is(1));

        } catch (IOException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkSecondTransactionsErrorValues() {
        try {
            List<Transaction> transactions = Json.parseTransactions(checkSecondTransactionWithErrorValues);
            assertThat(transactions, is(nullValue()));
        } catch (IOException e) {

        } catch (Exception e) {

        }
    }

    @Test
    public void checkSecondTransactionsWithNumbers() {
        try {
            List<Transaction> transactions = Json.parseTransactions(checkSecondTransactionWithNumberValues);
            assertThat(transactions, is(notNullValue()));
            assertThat(transactions.size(), is(4));
        } catch (IOException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
    }

}