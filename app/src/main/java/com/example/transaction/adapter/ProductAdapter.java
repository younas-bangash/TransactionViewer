package com.example.transaction.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.transaction.activity.MainActivity;
import com.example.transaction.activity.ProductHistoryActivity;
import com.example.transaction.R;
import com.example.transaction.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<Product> productList;
    private Context mContext;

    public ProductAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder,final int i) {
        final Product item = productList.get(i);
        customViewHolder.header.setText(item.name);
        customViewHolder.description.setText(item.getNumberOfTransactions(mContext));
        customViewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductHistoryActivity.class);
                intent.putExtra(MainActivity.EXTRA_PRODUCT, item);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return (null != productList ? productList.size() : 0);
    }
}