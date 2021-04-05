package com.ssit.newspaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ssit.newspaper.R;
import com.ssit.newspaper.communication.FragmentCommunication;
import com.ssit.newspaper.model.News;
import com.ssit.newspaper.viewHolder.NewsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private Context context;
    private List<News> newsList;
    private FragmentCommunication mCommunicator;
    public NewsAdapter(Context context, List<News> statusList,FragmentCommunication mCommunication) {
        this.context = context;
        this.newsList = statusList;
        this.mCommunicator=mCommunication;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.newspaper_layout,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News model=newsList.get(position);
        holder.tvNewsPaper.setText(model.bn_name);
        Picasso.with(context).load("https://mobile.mobilestorebd.com/images/newspaper/"+model.getN_image()).into(holder.ivNews);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommunicator.respond(model.getLink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


}
