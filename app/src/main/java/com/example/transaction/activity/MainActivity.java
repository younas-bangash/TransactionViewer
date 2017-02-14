package com.example.transaction.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.transaction.Callback;
import com.example.transaction.R;
import com.example.transaction.TransactionsService;
import com.example.transaction.adapter.ProductAdapter;
import com.example.transaction.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Callback, ServiceConnection {
    public static final String EXTRA_PRODUCT = "product_extra";
    private TransactionsService service;
    private static Map<String, Product> transactionMap;
    @BindView(R.id.my_recycler_view)
    protected RecyclerView recyclerView;
    private ProductAdapter adapter;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    //constants for running the app
    private final String FIRST = "first";
    private final String SECOND = "second";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        if (savedInstanceState == null) {
            Intent bidingIntent = new Intent(this, TransactionsService.class);
            bindService(bidingIntent, this, BIND_AUTO_CREATE);
        }else if( this.transactionMap !=null ) {
            List<Product> productList = new ArrayList<>(transactionMap.values());
            adapter = new ProductAdapter(MainActivity.this, productList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void getTransactions(Map<String, Product> transactionMap) {
        this.transactionMap = transactionMap;
        List<Product> productList = new ArrayList<Product>(transactionMap.values());
        adapter = new ProductAdapter(MainActivity.this, productList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.service = ((TransactionsService.LocalBinder) service).getService();
        this.service.getTransactions(FIRST, this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (service != null) {
            unbindService(this);
        }
        recyclerView.setAdapter(null);
    }
}
