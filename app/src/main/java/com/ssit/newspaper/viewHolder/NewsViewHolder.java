package com.ssit.newspaper.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ssit.newspaper.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNewsPaper;
    public ImageView ivNews;
    public CardView cardView;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNewsPaper = itemView.findViewById(R.id.tv_news_name);
        ivNews = itemView.findViewById(R.id.iv_news_paper);
        cardView = itemView.findViewById(R.id.cv_news);
    }
}
