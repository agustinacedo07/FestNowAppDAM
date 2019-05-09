package com.example.agustin.festnowapp;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class UsuarioWS extends AsyncTask<String,Void,String> {
	 String orden;
	 boolean respuestaBoolean;




	public UsuarioWS(String orden){
		this.orden = orden;
	}

	//descarga y procesa la URL de respuesta del WebService
	@Override
        protected String doInBackground(String... strings) {
            try{
                return WebServiceUtil.dowloadUrl(strings[0]);
            }catch (IOException ex){
                return "URL no válida";
            }
        }



        @Override
        protected void onPostExecute(String s) {
        	JSONArray jsonArray = null;


			switch(orden){
            //devuelve un json con todos los elementos o un mensaje de que no existen festivales
            case "login":

                if(s.contains("1")){
                    this.respuestaBoolean = true;
                }else{
                    this.respuestaBoolean = false;
                }
            break;





            }


		}


    public boolean isRespuestaBoolean() {
        return respuestaBoolean;
    }

    public void setRespuestaBoolean(boolean respuestaBoolean) {
        this.respuestaBoolean = respuestaBoolean;
    }
}