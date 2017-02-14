package com.example.transaction;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.transaction.Utility.Conversion;
import com.example.transaction.Utility.Json;
import com.example.transaction.model.Product;
import com.example.transaction.model.Rate;
import com.example.transaction.model.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TransactionsService extends Service {

    private LocalBinder mLocalBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    public void getTransactions(String fileName, Callback callback) {
        new TransactionOperation(callback).execute(fileName);
    }

    private class TransactionOperation extends AsyncTask<String, Void, Map<String, Product>> {
        Callback callback;

        public TransactionOperation(Callback callback) {
            this.callback = callback;
        }

        @Override
        protected Map<String, Product> doInBackground(String... params) {
            Map<String, Product> convertMap = null;
            try {
                List<Rate> rateList = Json.parseExchangeRates(getBaseContext(), params[0] + "_rates.json");
                List<Transaction> transactionList = Json.parseTransactions(getBaseContext(),
                        params[0] + "_transactions.json");
                convertMap = Conversion.convert(rateList, transactionList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return convertMap;
        }

        @Override
        protected void onPostExecute(Map<String, Product> result) {
            callback.getTransactions(result);
        }
    }

    public class LocalBinder extends Binder {
        public TransactionsService getService() {
            return TransactionsService.this;
        }
    }

}
