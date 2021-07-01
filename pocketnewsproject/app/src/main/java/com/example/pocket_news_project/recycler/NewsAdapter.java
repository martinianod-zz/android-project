package com.example.pocket_news_project.recycler;


import android.app.Application;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.pocket_news_project.MainActivity;
import com.example.pocket_news_project.MyOnCardViewClick;
import com.example.pocket_news_project.News;
import com.example.pocket_news_project.R;
import com.example.pocket_news_project.conexion.HiloConexion;

import java.util.List;

public class NewsAdapter extends Adapter<NewsViewHolder> implements Handler.Callback {

    News news;
    List<News> newsList;
    MyOnCardViewClick myOnCardViewClick;

    Context context;
    Handler handler;
    private NewsViewHolder holder;

    public NewsAdapter(List<News> newsList, MyOnCardViewClick myOnCardViewClick, Context applicationContext) {

        this.newsList = newsList;
        this.myOnCardViewClick = myOnCardViewClick;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item , parent ,false);

        NewsViewHolder newsViewHolder = new NewsViewHolder(view , myOnCardViewClick);

        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        this.holder = holder;

        news = newsList.get(position);
        holder.txtTitle.setText(news.getTitle());
        holder.txtDescription.setText(news.getDescription());


        handler = new Handler(this);
        HiloConexion hiloImages = new HiloConexion(handler , context , news.getUrlToImage() , position , true);
        hiloImages.start();


        holder.setPosition(position);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {

        if (message.obj != null){
            byte[] img = (byte[]) message.obj;

            if (message.arg1 == holder.getHolderPosition()){
                holder.imageNews.setImageBitmap(BitmapFactory.decodeByteArray(img , 0 , img.length));
                news.setNewsImage(img);
            }

        }

        return false;
    }
}
