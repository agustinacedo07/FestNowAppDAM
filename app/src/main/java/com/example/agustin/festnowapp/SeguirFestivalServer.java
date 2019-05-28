package com.example.agustin.festnowapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agustin.festnowapp.Util.SesionServer;

import java.io.IOException;

import modelos.Comando;
import modelos.Festival;

public class SeguirFestivalServer extends AsyncTask<String,Void,Boolean>{
    Context contexto;
    Festival festival;
    AdaptadorFestivalesUser adaptador;
    int posicionFestival;

    public SeguirFestivalServer(Context context,Festival festival,int posicionFestival,AdaptadorFestivalesUser adaptador){
        this.contexto = context;
        this.festival = festival;
        this.posicionFestival = posicionFestival;
        this.adaptador = adaptador;

    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean follow;
        String idCliente = strings[0];
        String idFestival = strings[1];

        modelos.Comando comando = new Comando();
        comando.setOrden("seguirFest");
        comando.getArgumentos().add(idCliente);
        comando.getArgumentos().add(idFestival);

        try {
            SesionServer.flujoSalidaObjetos.writeObject(comando);
            comando = (Comando) SesionServer.flujoEntradaObjetos.readObject();

             follow = comando.isRespuestaBooleana();


        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return follow ;
    }


    @Override
    protected void onPostExecute(Boolean follow) {
        if(follow){
            AdaptadorFestivalesUser.eliminarFestival(posicionFestival);
            Toast.makeText(contexto,"Ahora sigue a ..."+festival.getNombre(),Toast.LENGTH_LONG).show();
            adaptador.notifyDataSetChanged();
        }else{
            Toast.makeText(contexto,"Problema para seguir a el festival ... "+festival.getNombre(),Toast.LENGTH_LONG).show();
        }
    }
}
