package com.ssit.newspaper.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ssit.newspaper.R;
import com.ssit.newspaper.activity.NewsDetailsActivity;
import com.ssit.newspaper.communication.FragmentCommunication;
import com.ssit.newspaper.model.News;
import com.ssit.newspaper.viewHolder.NewsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder>implements Filterable {
    private Context context;
    private List<News> newsList;
    private FragmentCommunication mCommunicator;
    private List<News> exampleListFull;
    public NewsAdapter(Context context, List<News> newsList,FragmentCommunication mCommunication) {
        this.context = context;
        this.newsList = newsList;
        this.mCommunicator=mCommunication;
        exampleListFull = new ArrayList<>(newsList);
        notifyDataSetChanged();

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
               // mCommunicator.respond(model.getLink());
                Intent intent=new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("URL",model.getLink());
                intent.putExtra("NAME",model.getBn_name());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public Filter getFilter() {
        if(exampleFilter==null) {

            return null;

        }
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<News> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                // String.valueOf(item.getTotalWork())
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (News item : exampleListFull) {
                    if (item.getEn_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            newsList.clear();
            newsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public void filtering(List<News> listitem) {

        newsList = new ArrayList<>();
        newsList.addAll(listitem);
        notifyDataSetChanged();
    }
}
