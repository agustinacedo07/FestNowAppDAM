package com.example.agustin.festnowapp;

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

import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Comando;
import modelos.Comentario;
import modelos.Festival;

public class FragmentComentarios extends Fragment {
    static  Context contexto;
    Festival festival;
    public static ArrayList<Comentario>listaComentariosFestival = new ArrayList<Comentario>();


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
        View viewRoot = inflater.inflate(R.layout.paginacomentarios,container,false);


        ListView listaComentarios = (ListView)viewRoot.findViewById(R.id.listaComentarios);

        AdaptadorComentariosBasico adaptador = new AdaptadorComentariosBasico(contexto,listaComentariosFestival,listaComentarios,this);
        listaComentarios.setAdapter(adaptador);

        return viewRoot;


    }



    public static class ObtenerComentarios extends AsyncTask<Void,Void,Boolean>{
        private Festival festival;


        public ObtenerComentarios(Festival festival){
            this.festival = festival;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean obtencion = false;
            Comando comando = new Comando();
            comando.setOrden("comentariosFest");
            comando.getArgumentos().add(festival.getIdFestival());
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando)SesionUserServer.flujoEntradaObjetosUser.readObject();
                listaComentariosFestival = (ArrayList<Comentario>) comando.getArgumentos().get(0);
                for(int i=0;i<listaComentariosFestival.size();i++){
                    if(listaComentariosFestival.get(i).getCliente().getIdCliente() == SesionUserServer.clienteAplicacion.getIdCliente()){
                        PantallaPrincipalDelFestival.numPantallas = 5;
                        break;
                    }
                }

                obtencion = true;
            } catch (IOException e) {
                return false;
            } catch (ClassNotFoundException e) {
                return false;
            }

            return obtencion;
        }

        @Override
        protected void onPostExecute(Boolean obtencion) {
            if(!obtencion){
                Toast.makeText(contexto,"Problemas con la conexion al servidor",Toast.LENGTH_LONG).show();
            }
        }
    }
}
