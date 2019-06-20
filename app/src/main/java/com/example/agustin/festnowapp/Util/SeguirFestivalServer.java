package com.example.agustin.festnowapp.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.example.agustin.festnowapp.Adaptadores.AdaptadorFestivalesUser;
import java.io.IOException;

import modelos.Comando;
import modelos.Festival;

/**
 * Clase que realiza las operaciones, cuando el usuario sigue a un festival, para actualizar la inserción en la Base de datos
 * y actualizar la lista que se encuentra en la pantalla principal
 */
public class SeguirFestivalServer extends AsyncTask<String,Void,Boolean>{
    private Context contexto;
    private Festival festival;
    private AdaptadorFestivalesUser adaptador;
    private int posicionFestival;

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
            SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
            comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();

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
            adaptador.notifyDataSetChanged();//avisa a el adaptador que sus items han sido actualizados
        }else{
            Toast.makeText(contexto,"Problema para seguir a el festival ... "+festival.getNombre(),Toast.LENGTH_LONG).show();
        }
    }
}
