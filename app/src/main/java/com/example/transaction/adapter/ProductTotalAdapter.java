package com.example.transaction.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.transaction.R;
import com.example.transaction.model.Pair;

import java.util.List;

public class ProductTotalAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<Pair> pairList;
    private Context mContext;

    public ProductTotalAdapter(Context context, List<Pair> productList) {
        this.pairList = productList;
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
        final Pair item = pairList.get(i);
        customViewHolder.header.setText(ssetCurrenySign(item.oldCurrency));
        customViewHolder.description.setText(String.valueOf(item.priceGbp));
        Log.d("TAG",""+item.oldCurrency);

    }

    private String ssetCurrenySign(String currecy){
        String mReturnCurrencySign="";
        String[] separated = currecy.split(" ");
        String mCurrency = separated[0];
        String amount = separated[1];
        switch (mCurrency){
            case "USD":
                mReturnCurrencySign ="$";
                break;
            case "AUD":
                mReturnCurrencySign ="A$";
                break;
            case "CAD":
                mReturnCurrencySign ="CA$";
                break;
            case "GBP":
                mReturnCurrencySign ="£";
                break;
            default:
                mReturnCurrencySign = mCurrency;
                break;
        }
        return mReturnCurrencySign+amount;
    }

    @Override
    public int getItemCount() {
        return (null != pairList ? pairList.size() : 0);
    }
}