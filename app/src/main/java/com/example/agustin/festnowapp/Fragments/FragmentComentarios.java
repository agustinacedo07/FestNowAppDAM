package com.example.agustin.festnowapp.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agustin.festnowapp.Adaptadores.AdaptadorComentariosBasico;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Comando;
import modelos.Comentario;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase que representa a el fragment que muestra la informacion de los comentarios del festival
 *
 */
public class FragmentComentarios extends Fragment {

    static Context contexto;
    private ListView listViewComentarios;


    public static FragmentComentarios newInstance(Festival festival, Context contextoPadre) {
        Bundle args = new Bundle();
        contexto = contextoPadre;
        args.putSerializable("festival",festival);
        FragmentComentarios fragment = new FragmentComentarios();
        fragment.setArguments(args);
        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Festival festival = (Festival)getArguments().getSerializable("festival");

        View viewRoot = inflater.inflate(R.layout.pagina_comentarios,container,false);
        listViewComentarios = (ListView)viewRoot.findViewById(R.id.listaComentarios);
        new FragmentComentarios.ObtenerComentarios(festival,listViewComentarios).execute();


        return viewRoot;

    }


    //*********** GETTET Y SETTERS **********

    public static Context getContexto() {
        return contexto;
    }

    public static void setContexto(Context contexto) {
        FragmentComentarios.contexto = contexto;
    }

    public ListView getListViewComentarios() {
        return listViewComentarios;
    }

    public void setListViewComentarios(ListView listViewComentarios) {
        this.listViewComentarios = listViewComentarios;
    }


    /**
     * Hilo que ejecuta en segundo plano para traer todos los comentarios del festival de la BD
     */
    private class ObtenerComentarios extends AsyncTask<Void,Void,ArrayList<Comentario>>{
        private Festival festival;
        private ListView listViewComentarios;


        public ObtenerComentarios(Festival festival,ListView listViewComentarios){
            this.festival = festival;
            this.listViewComentarios = listViewComentarios;
        }


        @Override
        protected ArrayList<Comentario> doInBackground(Void... voids) {
            Comando comando = new Comando();
            comando.setOrden("comentariosFest");
            comando.getArgumentos().add(festival.getIdFestival());
            ArrayList<Comentario> listaComentarios = new ArrayList<Comentario>();
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando)SesionUserServer.flujoEntradaObjetosUser.readObject();
                listaComentarios = (ArrayList<Comentario>) comando.getArgumentos().get(0);
            } catch (IOException e) {
                Toast.makeText(contexto,"Problemas con la comunicación",Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(contexto,"Problemas con la comunicación",Toast.LENGTH_LONG).show();
            }
            return listaComentarios;
        }

        @Override
        protected void onPostExecute(ArrayList<Comentario> lista) {
           if(lista.size()==0){
               Toast.makeText(contexto,"El festival: "+festival.getNombre()+" no tiene seguidores",Toast.LENGTH_LONG).show();
           }else{
               AdaptadorComentariosBasico adaptador = new AdaptadorComentariosBasico(contexto,lista,listViewComentarios);
               listViewComentarios.setAdapter(adaptador);

           }
        }
    }
}
