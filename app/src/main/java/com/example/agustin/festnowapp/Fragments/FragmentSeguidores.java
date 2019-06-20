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

import com.example.agustin.festnowapp.Adaptadores.AdaptadorSeguidoresBasico;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Cliente;
import modelos.Comando;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase que representa al fragment dinamico que contiene la informacion de los seguidores de el festival
 */
public class FragmentSeguidores extends Fragment {
    static Context contexto;
    private  ListView listViewSeguidores;

    public static FragmentSeguidores newInstance(Festival festival,Context contextoPadre) {
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
        Festival festival = (Festival) getArguments().getSerializable("festival");
        View viewRoot = inflater.inflate(R.layout.pagina_seguidores,container,false);


        listViewSeguidores = (ListView)viewRoot.findViewById(R.id.listaSeguidores);
        new FragmentSeguidores.ObtenerSeguidores(festival,listViewSeguidores).execute();

        return viewRoot;
    }





    /**
     * Hilo que se ejecuta en segundo plano para obtener de la BD los seguidores del festival
     */
    private  class ObtenerSeguidores extends AsyncTask<Void,Void,ArrayList<Cliente>>{
        private Festival festival;
        private ListView listViewSeguidores;

        public ObtenerSeguidores(Festival festival,ListView listViewSeguidores){
            this.festival = festival;
            this.listViewSeguidores = listViewSeguidores;
        }

        @Override
        protected ArrayList<Cliente> doInBackground(Void... voids) {
            Comando comando = new Comando();
            comando.setOrden("seguidoresFest");
            comando.getArgumentos().add(festival.getIdFestival());
            ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
                listaClientes = (ArrayList<Cliente>) comando.getArgumentos().get(0);
            } catch (IOException e) {
                Toast.makeText(contexto,"Problemas con la comunicación",Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(contexto,"Problemas con la comunicación",Toast.LENGTH_LONG).show();
            }
            return listaClientes;
        }

        @Override
        protected void onPostExecute(ArrayList<Cliente> lista) {
           if(lista.size()==0){
               Toast.makeText(contexto,"No existen artistas para el Festival: "+festival.getNombre(),Toast.LENGTH_LONG).show();
           }else{
               AdaptadorSeguidoresBasico adaptador = new AdaptadorSeguidoresBasico(contexto,lista,listViewSeguidores);
               listViewSeguidores.setAdapter(adaptador);
           }
        }
    }
}
