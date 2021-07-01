package com.example.pocket_news_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pocket_news_project.recycler.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyNewsListActivity extends AppCompatActivity implements MyOnCardViewClick {

    List<News> listNewsSaved = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My News List");
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences mySavedNews = getSharedPreferences("list_news", Context.MODE_PRIVATE);
        mySavedNews.edit();


        //recuperar noticias del sharedpreference
        RecyclerView list = (RecyclerView)findViewById(R.id.rvMyNewsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);



        String listSavedString = mySavedNews.getString("list_news", "");
        try {

            JSONArray jsonArray = new JSONArray(listSavedString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                News newsSaved = new News();
                newsSaved.setTitle(jsonObject.getString("title"));
                newsSaved.setDescription(jsonObject.getString("description"));
                newsSaved.setUrlToImage(jsonObject.getString("urlToImage"));
                newsSaved.setPublishedAt(jsonObject.getString("publishedAt"));
                newsSaved.setUrl(jsonObject.getString("url"));
                newsSaved.setSaved(true);

                listNewsSaved.add(newsSaved);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NewsAdapter adapter = new NewsAdapter(listNewsSaved , this , getApplicationContext() );
        list.setAdapter(adapter);



    }

    @Override
    protected void onRestart() {

        Log.d("onRestart()" , "actualizar lista de noticias");

        List<News> listNewsUpdated = new ArrayList<>();

        SharedPreferences mySavedNews = getSharedPreferences("list_news", Context.MODE_PRIVATE);
        mySavedNews.edit();

        //recuperar noticias del sharedpreference
        RecyclerView list = (RecyclerView)findViewById(R.id.rvMyNewsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);


        String listSavedString = mySavedNews.getString("list_news", "");
        try {

            JSONArray jsonArray = new JSONArray(listSavedString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                News newsSaved = new News();
                newsSaved.setTitle(jsonObject.getString("title"));
                newsSaved.setDescription(jsonObject.getString("description"));
                newsSaved.setUrlToImage(jsonObject.getString("urlToImage"));
                newsSaved.setPublishedAt(jsonObject.getString("publishedAt"));
                newsSaved.setUrl(jsonObject.getString("url"));
                newsSaved.setSaved(true);

                listNewsUpdated.add(newsSaved);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NewsAdapter adapter = new NewsAdapter(listNewsUpdated , this , getApplicationContext() );
        list.setAdapter(adapter);

        super.onRestart();
    }

    //menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            super.finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public void onCardViewClick(int position) {

        News news = listNewsSaved.get(position);

        Intent i = new Intent(this, NewsFullActivity.class);

        i.putExtra("news" , (Serializable) news);

        startActivity(i);

    }
}