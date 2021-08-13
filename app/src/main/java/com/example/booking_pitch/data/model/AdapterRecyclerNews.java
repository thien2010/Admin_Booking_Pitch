package com.example.booking_pitch.data.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.Admin.NewsActivity;
import com.example.booking_pitch.Admin.PitchActivity;
import com.example.booking_pitch.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterRecyclerNews extends RecyclerView.Adapter<AdapterRecyclerNews.NewsViewHoler>{

    Context context;
    List<News> newsList;

    public AdapterRecyclerNews(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public NewsViewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_all_news,parent,false);
        return new NewsViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsViewHoler holder, int position) {
        News news = newsList.get(position);
        if (news == null){
            return;
        }
        holder.title.setText(news.getTitle());
        holder.content.setText(news.getContent());
        Glide.with(context)
                .load("http://datn-2021.herokuapp.com"+news.getImage())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",news.getTitle());
                bundle.putString("content",news.getContent());
                bundle.putString("imgnews",news.getImage());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (newsList!=null){
            return newsList.size();
        }
        return 0;
    }

    public class NewsViewHoler extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title ;
        TextView content ;
        RecyclerView recyclerView;
        public NewsViewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_news);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            recyclerView = itemView.findViewById(R.id.rcv_get_all_new);
        }
    }
}
