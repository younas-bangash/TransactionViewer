package com.example.transaction.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.transaction.R;
import com.example.transaction.adapter.ProductTotalAdapter;
import com.example.transaction.model.Product;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductHistoryActivity extends AppCompatActivity {

    private Product product;

    @BindView(R.id.my_recycler_view)
    protected RecyclerView recyclerView;
    private ProductTotalAdapter adapter;
    @BindView(R.id.sum)
    protected TextView sum;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getIntent().getSerializableExtra(MainActivity.EXTRA_PRODUCT);
        setContentView(R.layout.activity_product_history);

        ButterKnife.bind(this);

        if (product != null) {
            configureToolbar();

            sum.setText(MessageFormat.format("{0}{1}", getResources().getString(R.string.total_sterling),
                    String.valueOf(product.sum)));
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                    LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);

            adapter = new ProductTotalAdapter(ProductHistoryActivity.this, product.priceHistory);
            recyclerView.setAdapter(adapter);
        }
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) return;

        setTitle(getResources().getString(R.string.title_transactions) + " " + product.name);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),
                R.color.title_text_color));
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
    }

}
