package com.example.pocket_news_project.conexion;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.pocket_news_project.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HiloConexion extends Thread {

    public static final int IMAGEN = 1;
    public static final int NOTICIAS = 2;

    private int position;
    private String urlString;
    private Handler handler;

    private boolean isImage;

    private Context applicationContext;

    public HiloConexion(Handler handler, Context applicationContext, String url, int position, boolean isImage) {

        this.position = position;
        this.handler = handler;
        this.applicationContext = applicationContext;
        this.urlString = url;
        this.isImage = isImage;
    }


    @Override
    public void run() {

        ConexionHTTP conexionHTTP = new ConexionHTTP();

        byte[] imagesJson;
        if (isImage){

            try{
                imagesJson = conexionHTTP.obtenerRespuesta(urlString);
                Message msg = new Message();
                msg.obj = imagesJson;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }catch (RuntimeException e){
                Message msg = new Message();
                msg.obj = null;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }

        }else {

            //byte[] newsJson = conexionHTTP.obtenerRespuesta("http://api.mediastack.com/v1/news?access_key=f54a3fd41406f035a3ce65504967147f&languages=es,en" );
            //byte[] newsJson = conexionHTTP.obtenerRespuesta("http://api.mediastack.com/v1/news?access_key=f54a3fd41406f035a3ce65504967147f&sort=popularity&languages=es,en&limit=100" );

            byte[] newsJson = conexionHTTP.obtenerRespuesta(urlString);

            String jsonString = new String(newsJson);
            /*try {
                InputStream is = applicationContext.getAssets().open("news.json");

                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                jsonString = new String(buffer, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }*/


            //String s = new String(jsonString);

            Message msg = new Message();
            msg.obj = this.parserJson(jsonString);

            handler.sendMessage(msg);

        }

    }

    public List<News> parserJson(String s) {

        List<News> newsList = new ArrayList<>();

        try {

            JSONObject newsListJson = new JSONObject(s);
            JSONArray articles = newsListJson.getJSONArray("data");
            //JSONArray jsonArray = new JSONArray(s);

            for (int i = 0; i < articles.length(); i++) {

                JSONObject jsonObject = articles.getJSONObject(i);

                if(!jsonObject.getString("image").equals("null")){

                    News news = new News();

                    news.setTitle(jsonObject.getString("title"));
                    news.setDescription(jsonObject.getString("description"));
                    news.setUrl(jsonObject.getString("url"));
                    news.setUrlToImage(jsonObject.getString("image"));
                    news.setPublishedAt(jsonObject.getString("published_at"));
                    news.setSaved(false);

                    newsList.add(news);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsList;

    }


}


