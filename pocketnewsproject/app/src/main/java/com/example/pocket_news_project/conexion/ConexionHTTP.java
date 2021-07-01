package com.example.pocket_news_project.conexion;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ConexionHTTP {

    public byte[] obtenerRespuesta(String urlString){
        try {

                URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            int respuesta = urlConnection.getResponseCode();

            Log.d("Conexion" , "Respuesta del servidor " + respuesta);

            if (respuesta == 200){

                InputStream is = urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int cant = 0;

                while ((cant = is.read(buffer)) != -1){

                    baos.write(buffer , 0 , cant);

                }

                is.close();

                //return baos.toString();
                return baos.toByteArray();

            }else {
                throw new RuntimeException("Error en la conexion con el serv " );
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        } catch (IOException e) {

            e.printStackTrace();
            throw  new RuntimeException(e);
        }

        //   return  null;
    }
}
