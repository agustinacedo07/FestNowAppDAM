package com.example.agustin.festnowapp;

import android.content.Context;
import android.content.Intent;
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

import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Artista;
import modelos.Comando;
import modelos.Festival;

public class FragmentArtistas extends Fragment {

    public static ArrayList<Artista> artistasFestival = new ArrayList<Artista>();
   static Context contexto;
    Festival festival;
    static ListView listViewArtistas;




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
        festival = (Festival)getArguments().getSerializable("festival");
        new FragmentArtistas.ObtenerArtistasFestival(festival).execute();


        View rootView = inflater.inflate(R.layout.paginaartistas,container,false);



        //componentes de la vista
         listViewArtistas = (ListView)rootView.findViewById(R.id.listaArtistasGeneral);







        return rootView;


    }

    public static class ObtenerArtistasFestival extends AsyncTask<Void,Void,Object>{
        private Festival festival;

        public ObtenerArtistasFestival(Festival festival){
            this.festival = festival;

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
                return  null;
            } catch (ClassNotFoundException e) {
                return null;
            }

            return listaArtistas;



        }

        @Override
        protected void onPostExecute(Object objeto) {
           ArrayList<Artista> listaArtistas = (ArrayList<Artista>)objeto;
           if(listaArtistas!=null){
               if(listaArtistas.size()==0){
                   Toast.makeText(contexto,"No existen artistas para el festival: "+festival.getNombre(),Toast.LENGTH_LONG).show();
               }else{
                   AdaptadorArtistaBasico adaptadorListadoArtistasBasico = new AdaptadorArtistaBasico(contexto,listaArtistas,listViewArtistas,festival);
                   listViewArtistas.setAdapter(adaptadorListadoArtistasBasico);
               }
           }
        }
    }


}
