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
        View rootView = inflater.inflate(R.layout.paginaartistas,container,false);

         festival = (Festival)getArguments().getSerializable("festival");


        //componentes de la vista
        ListView listaArtistas = (ListView)rootView.findViewById(R.id.listaArtistasGeneral);



        AdaptadorArtistaBasico adaptadorListadoArtistasBasico = new AdaptadorArtistaBasico(contexto,artistasFestival,listaArtistas,festival,this);
        listaArtistas.setAdapter(adaptadorListadoArtistasBasico);



        return rootView;


    }

    public static class ObtenerArtistasFestival extends AsyncTask<Void,Void,Boolean>{
        private Festival festival;

        public ObtenerArtistasFestival(Festival festival){
            this.festival = festival;

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean obtencionArtistas = false;
            Comando comando = new Comando();
            comando.setOrden("artistasFest");
            comando.getArgumentos().add(festival.getIdFestival());

            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
                artistasFestival = (ArrayList<Artista>) comando.getArgumentos().get(0);
                obtencionArtistas = true;
            } catch (IOException e) {
                return  false;
            }finally {
                return obtencionArtistas;
            }



        }

        @Override
        protected void onPostExecute(Boolean obtencionArtistas) {
            if(obtencionArtistas){
                if(artistasFestival.size()==0){
                    Toast.makeText(contexto,"No existen artistas para el festival "+festival.getNombre(),Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(contexto,"Problemas con la conexion",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void detalleArtista(Artista artista) {

            Intent pantallaDetalleArtista = new Intent(contexto, PantallaDetalleArtista.class);
            pantallaDetalleArtista.putExtra("artista", artista);
            pantallaDetalleArtista.putExtra("festival",festival);
            startActivity(pantallaDetalleArtista);



    }
}
