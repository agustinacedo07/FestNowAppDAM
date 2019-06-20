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

import com.example.agustin.festnowapp.Adaptadores.AdaptadorArtistaBasico;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Artista;
import modelos.Comando;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase que representa al fragment con la informacion detallada de los artistas que participan en el festival
 */
public class FragmentArtistas extends Fragment {
    //variables estaticas para que puedan usarse a nivel de clase desde el hilo que se ejecuta en segundo plano
    static Context contexto;
    private ListView listViewArtistas;




    public static FragmentArtistas newInstance(Festival festival,Context contextoPadre) {
        Bundle args = new Bundle();
        args.putSerializable("festival",festival);
        contexto = contextoPadre;
        FragmentArtistas fragment = new FragmentArtistas();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Festival festival = (Festival)getArguments().getSerializable("festival");


        View rootView = inflater.inflate(R.layout.pagina_artistas,container,false);
        //componentes de la vista
         listViewArtistas = (ListView)rootView.findViewById(R.id.listaArtistasGeneral);
        new FragmentArtistas.ObtenerArtistasFestival(festival,listViewArtistas ).execute();


        return rootView;
    }


    //***************** GETTERS Y SETTERS **********************
    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public ListView getListViewArtistas() {
        return listViewArtistas;
    }

    public void setListViewArtistas(ListView listViewArtistas) {
        this.listViewArtistas = listViewArtistas;
    }









    /**
     * Hilo que se ejecuta en segundo plano para contruir la información del fragment
     */
    private  class ObtenerArtistasFestival extends AsyncTask<Void,Void,Object>{
        private Festival festival;
        private ListView listViewArtistas;

        public ObtenerArtistasFestival(Festival festival,ListView listViewArtistas){
            this.festival = festival;
            this.listViewArtistas = listViewArtistas;

        }

        @Override
        protected Object doInBackground(Void... voids) {
            Comando comando = new Comando();
            comando.setOrden("artistasFest");
            comando.getArgumentos().add(festival.getIdFestival());
            ArrayList<Artista> listaArtistas = new ArrayList<Artista>();

            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
                listaArtistas = (ArrayList<Artista>) comando.getArgumentos().get(0);
            } catch (IOException e) {
                Toast.makeText(contexto,"Problemas con la comunicación",Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(contexto,"Problemas con la comunicación",Toast.LENGTH_LONG).show();
            }

            return listaArtistas;

        }

        @Override
        protected void onPostExecute(Object lista) {
           ArrayList<Artista> listaArtistas = (ArrayList<Artista>)lista;
           if(listaArtistas!=null){
               if(listaArtistas.size()==0){
                   Toast.makeText(contexto,"No existen artistas para el festival: "+festival.getNombre(),Toast.LENGTH_LONG).show();
               }else{
                   AdaptadorArtistaBasico adaptadorListadoArtistasBasico = new AdaptadorArtistaBasico(contexto,listaArtistas,festival);
                   listViewArtistas.setAdapter(adaptadorListadoArtistasBasico);
               }
           }
        }
    }


}
