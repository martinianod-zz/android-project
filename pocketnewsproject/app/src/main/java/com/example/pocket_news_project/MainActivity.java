package com.example.pocket_news_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pocket_news_project.conexion.HiloConexion;
import com.example.pocket_news_project.recycler.NewsAdapter;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MyOnCardViewClick, Handler.Callback {

    List<News> newsList;

    NewsAdapter adapter;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pocket News");

        /*SharedPreferences MySavedNews = getSharedPreferences("list_news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MySavedNews.edit();
        editor.clear();
        editor.apply();*/

        handler = new Handler(this);

        HiloConexion hiloNews = new HiloConexion(handler, getApplicationContext(), "http://api.mediastack.com/v1/news?access_key=f54a3fd41406f035a3ce65504967147f&sort=popularity&languages=es,en&limit=100", 0, false);
        hiloNews.start();

/*        List<News> newsList = new ArrayList<News>();
        newsList.add(new News("title1","description 1" , null , null , null , "20:01"));
        newsList.add(new News("title2","description 2" , null , null , null , "15:30"));
        newsList.add(new News("title3","description 3" , null , null , null , "12:51"));





        RecyclerView list = (RecyclerView)findViewById(R.id.rvNews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(newsList);
        list.setAdapter(adapter);


 */


    }

    @Override
    public boolean handleMessage(@NonNull Message message) {

        newsList = (List<News>) message.obj;

        RecyclerView list = (RecyclerView) findViewById(R.id.rvNews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(newsList, this, getApplicationContext());
        list.setAdapter(adapter);


        return false;
    }

    @Override
    public void onCardViewClick(int position) {

        News news = newsList.get(position);

        Intent i = new Intent(this, NewsFullActivity.class);

        i.putExtra("news", (Serializable) news);

        startActivity(i);

    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);

        MenuItem menuItem = menu.findItem(R.id.buscar);

        androidx.appcompat.widget.SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.discover) {

            Log.d("Se hizo click", "Desplegar Dialog");

            MiDialog miDialog = new MiDialog( handler, getApplicationContext());

            miDialog.show(getSupportFragmentManager(), "dialogo");


            return true;
        }

        if (item.getItemId() == R.id.my_news) {

            Log.d("Show My News", "mostrar las noticias guardadas");

            Intent i = new Intent(this, MyNewsListActivity.class);
            startActivity(i);


        }

        return super.onOptionsItemSelected(item);
    }

    // buscador
    @Override
    public boolean onQueryTextSubmit(String query) {

        Log.d("onQueryTextSubmit()", "Buscar por palabra completa");

        HiloConexion hiloNews = new HiloConexion(handler, getApplicationContext(), "http://api.mediastack.com/v1/news?access_key=f54a3fd41406f035a3ce65504967147f&sort=popularity&languages=es,en&limit=100&keywords=" + query, 0, false);
        hiloNews.start();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Log.d("onQueryTextChange()", "Buscar por coincidencia de caracteres");

        HiloConexion hiloNews = new HiloConexion(handler, getApplicationContext(), "http://api.mediastack.com/v1/news?access_key=f54a3fd41406f035a3ce65504967147f&sort=popularity&languages=es,en&limit=100&keywords=" + newText, 0, false);
        hiloNews.start();

        return true;
    }
}