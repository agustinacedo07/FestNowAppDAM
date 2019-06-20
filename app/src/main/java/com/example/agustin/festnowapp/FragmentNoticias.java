package com.example.agustin.festnowapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import modelos.Comando;
import modelos.Festival;
import modelos.Noticia;

public class FragmentNoticias extends Fragment {

    public static ArrayList<Noticia> listaNoticiasFestival;
    private static Festival festivalNoticas;
     static Context conexto;



    public static FragmentNoticias newInstance(Festival festival, Context contextoPadre) {
        festivalNoticas = festival;
        conexto = contextoPadre;



        Bundle args = new Bundle();

        args.putSerializable("festival",festival);

        FragmentNoticias fragment = new FragmentNoticias();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Festival festival = (Festival) getArguments().getSerializable("festival");
        new FragmentNoticias.ObtenerNoticias(festival).execute();


        View rootView = inflater.inflate(R.layout.paginanoticias,container,false);

        ListView listaNoticias = (ListView)rootView.findViewById(R.id.listaNoticias);

        AdaptadorNoticiaBasico adaptador = new AdaptadorNoticiaBasico(conexto,listaNoticiasFestival,listaNoticias,this);
        listaNoticias.setAdapter(adaptador);


        return rootView;

    }


    public static class ObtenerNoticias extends AsyncTask<Void,Void,Boolean>{
        Festival festival;

        public ObtenerNoticias(Festival festival){
            this.festival = festival;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean obtencion = false;
            Comando comando = new Comando();
            comando.setOrden("noticiasFest");
            comando.getArgumentos().add(festival.getIdFestival());
            try {

                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
                listaNoticiasFestival = (ArrayList<Noticia>) comando.getArgumentos().get(0);
                obtencion = true;
            } catch (IOException e) {
                return false;
            } catch (ClassNotFoundException e) {
                Toast.makeText(conexto,"Problema con la conexion del Servidor",Toast.LENGTH_LONG).show();

            }
            return obtencion;
        }

        @Override
        protected void onPostExecute(Boolean obtencion) {
            if(obtencion){
                if(listaNoticiasFestival.size()==0){
                    Toast.makeText(conexto,"No existen noticias para este festival",Toast.LENGTH_LONG).show();

                }
            }else{
                Toast.makeText(conexto,"Problema con la conexion del Servidor",Toast.LENGTH_LONG).show();

            }
        }
    }


    public void lanzarPaginaNoticia(String enlace){
        Intent lanzarWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(enlace));
        startActivity(lanzarWeb);
    }
}
