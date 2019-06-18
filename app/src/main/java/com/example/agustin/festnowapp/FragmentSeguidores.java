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

import modelos.Cliente;
import modelos.Comando;

public class FragmentSeguidores extends Fragment {
    static Context contexto;
    public static ArrayList<Cliente> listaSeguidoresFestival = new ArrayList<Cliente>();

    public static FragmentSeguidores newInstance(modelos.Festival festival,Context contextoPadre) {

        Bundle args = new Bundle();

        args.putSerializable("festival",festival);
        contexto = contextoPadre;

        FragmentSeguidores fragment = new FragmentSeguidores();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.paginaseguidores,container,false);

        modelos.Festival festival = (modelos.Festival) getArguments().getSerializable("festival");

        ListView listaSeguidores = (ListView)viewRoot.findViewById(R.id.listaSeguidores);

        AdaptadorSeguidoresBasico adaptador = new AdaptadorSeguidoresBasico(contexto,listaSeguidoresFestival,listaSeguidores,this);
        listaSeguidores.setAdapter(adaptador);

        //adaptador

        return viewRoot;
    }


    public static class ObtenerSeguidores extends AsyncTask<Void,Void,Boolean>{
        modelos.Festival festival;

        public ObtenerSeguidores(modelos.Festival festival){
            this.festival = festival;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean obtencion = false;
            Comando comando = new Comando();
            comando.setOrden("seguidoresFest");
            comando.getArgumentos().add(festival.getIdFestival());
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
                listaSeguidoresFestival = (ArrayList<Cliente>) comando.getArgumentos().get(0);
                obtencion =  true;
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
                Toast.makeText(contexto,"Problema de comunicación con el servidor",Toast.LENGTH_LONG).show();
            }
        }
    }
}
