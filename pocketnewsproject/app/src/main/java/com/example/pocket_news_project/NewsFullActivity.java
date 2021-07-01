package com.example.pocket_news_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.NumericShaper;
import java.util.ArrayList;
import java.util.List;

public class NewsFullActivity extends AppCompatActivity {

    //SharedPreferences MySavedNews;
    //SharedPreferences.Editor editor;

    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Extended News");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();


        news = (News) intent.getSerializableExtra("news");

        /*Bundle extras = intent.getExtras();


        TextView tvTitle = findViewById(R.id.titleFull);
        tvTitle.setText(news.getTitle());

        TextView tvDescription = findViewById(R.id.descriptionFull);
        tvDescription.setText(news.getContent());

        ImageView imageView = findViewById(R.id.imgNews);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(news.getNewsImage() , 0 , news.getNewsImage().length));*/

        WebView webview = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(news.getUrl());


        FloatingActionButton sharedButton = findViewById(R.id.floating_action_button);
        sharedButton.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareUrl = news.getUrl();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Url To News");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareUrl);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.finish();
        }

        if (item.getItemId() == R.id.save_news) {
            Log.d("Save News", "guardar noticia en shared preference");

            saveNews(news);

        }

        if (item.getItemId() == R.id.delete_news) {
            Log.d("Delete News", "eliminar noticia del shared preference");

            deleteNews(news);

        }

        return super.onOptionsItemSelected(item);
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (news.isSaved()) {
            getMenuInflater().inflate(R.menu.menu_my_news, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_news, menu);
        }


        //MenuItem menuItem = menu.findItem(R.id.buscar);

       /* androidx.appcompat.widget.SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);*/

        return true;
    }

    private void saveNews(News news) {

        SharedPreferences MySavedNews = getSharedPreferences("list_news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MySavedNews.edit();

        List<News> listUpdated = new ArrayList<>();
        String listNewsString = MySavedNews.getString("list_news", "");

        if (listNewsString.equals("")) {
            news.setSaved(true);
            listUpdated.add(news);
        } else {
            try {
                JSONArray jsonArray = new JSONArray(listNewsString);
                boolean newsAlreadySaved = false;
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.getString("url").equals(news.getUrl())) {
                        newsAlreadySaved = true;
                    }

                    News newsToLoad = new News();
                    newsToLoad.setTitle(jsonObject.getString("title"));
                    newsToLoad.setDescription(jsonObject.getString("description"));
                    newsToLoad.setUrlToImage(jsonObject.getString("urlToImage"));
                    newsToLoad.setPublishedAt(jsonObject.getString("publishedAt"));
                    newsToLoad.setUrl(jsonObject.getString("url"));
                    newsToLoad.setSaved(true);

                    listUpdated.add(newsToLoad);

                }

                if (!newsAlreadySaved) {
                    listUpdated.add(news);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        editor.putString("list_news", listUpdated.toString());

        editor.commit();

    }

    private void deleteNews(News news) {

        SharedPreferences MySavedNews = getSharedPreferences("list_news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MySavedNews.edit();

        List<News> listUpdated = new ArrayList<>();
        String listNewsString = MySavedNews.getString("list_news", "");

        if (listNewsString.equals("")) {
            news.setSaved(true);
            listUpdated.add(news);
        } else {
            try {
                JSONArray jsonArray = new JSONArray(listNewsString);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (!jsonObject.getString("url").equals(news.getUrl())) {
                        News newsToLoad = new News();
                        newsToLoad.setTitle(jsonObject.getString("title"));
                        newsToLoad.setDescription(jsonObject.getString("description"));
                        newsToLoad.setUrlToImage(jsonObject.getString("urlToImage"));
                        newsToLoad.setPublishedAt(jsonObject.getString("publishedAt"));
                        newsToLoad.setUrl(jsonObject.getString("url"));
                        newsToLoad.setSaved(true);

                        listUpdated.add(newsToLoad);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        editor.putString("list_news", listUpdated.toString());

        editor.commit();
        editor.apply();

    }
}