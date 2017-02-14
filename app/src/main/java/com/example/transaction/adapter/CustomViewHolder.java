package com.example.transaction.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.transaction.R;

import butterknife.BindView;
import butterknife.ButterKnife;


class CustomViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_header)
    public TextView header;
    @BindView(R.id.item_description)
    public TextView description;
    private View item;

    public CustomViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        item = itemView;
    }

    public View getView() {
        return item;
    }
}