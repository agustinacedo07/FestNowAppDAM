package com.example.agustin.festnowapp;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceUtil {
    public static String dowloadUrl(String string) throws IOException{
        Log.i("URL",""+string);
        string=string.replace(" ","%20");
        InputStream flujo=null;
        int len=500;
        try {
            String s="";
            try{
                URL url=new URL(string);
                HttpURLConnection conexion=(HttpURLConnection)url.openConnection();
                conexion.setReadTimeout(1000);
                conexion.setConnectTimeout(15000);
                conexion.setRequestMethod("GET");
                conexion.setDoInput(true);
                conexion.connect();
                int repose=conexion.getResponseCode();
                Log.i("respuesta","La respuesta es "+repose);

                flujo=conexion.getInputStream();

                String contentAsString= readIt(flujo,len);

                return contentAsString;
            }catch (Exception e){
                s+=e;
            }

            return  null;
        }finally {
            if(flujo!=null){
                flujo.close();
            }

        }
    }

    public static String readIt(InputStream flujo, int len) throws IOException{
        Reader reader=null;
        reader=new InputStreamReader(flujo,"UTF-8");
        char[] buffer=new char[len];
        reader.read(buffer);

        return  new String(buffer);
    }
}
