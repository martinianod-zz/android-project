package com.example.pocket_news_project.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.pocket_news_project.MyOnCardViewClick;
import com.example.pocket_news_project.R;

public class NewsViewHolder extends ViewHolder implements View.OnClickListener {

    private MyOnCardViewClick myOnCardViewClick;
    private int position;

    TextView txtTitle;
    TextView txtDescription;
    TextView txtContent;
    ImageView imageNews;

    public NewsViewHolder(@NonNull View itemView, MyOnCardViewClick myOnCardViewClick) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.title);
        txtDescription = itemView.findViewById(R.id.description);
        imageNews = itemView.findViewById(R.id.imgNews);
       // txtContent = itemView.findViewById(R.id.textView3);

        this.myOnCardViewClick = myOnCardViewClick;
        itemView.setOnClickListener(this);
    }

    public void setPosition(int position){
        this.position = position;
    }


    public int getHolderPosition(){
        return this.position;
    }

    @Override
    public void onClick(View view) {
        myOnCardViewClick.onCardViewClick(position);
    }
}
