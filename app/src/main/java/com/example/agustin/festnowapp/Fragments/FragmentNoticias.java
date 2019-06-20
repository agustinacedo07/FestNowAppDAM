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

import com.example.agustin.festnowapp.Adaptadores.AdaptadorNoticiaBasico;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Comando;
import modelos.Festival;
import modelos.Noticia;

/**
 * @author Agustin/Adrian
 * Clase que representa al fragment de la pantalla de noticias en el detalle de festival
 */
public class FragmentNoticias extends Fragment {

     private ArrayList<Noticia> listaNoticiasFestival;
     private Festival festivalNoticas;
     static Context conexto;



    public static FragmentNoticias newInstance(Festival festivalNoticas,Context contextoPadre) {
        Bundle args = new Bundle();
        conexto = contextoPadre;
        args.putSerializable("festival",festivalNoticas);
        FragmentNoticias fragment = new FragmentNoticias();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Festival festival = (Festival) getArguments().getSerializable("festival");

        View rootView = inflater.inflate(R.layout.pagina_noticias,container,false);
        ListView listaNoticias = (ListView)rootView.findViewById(R.id.listaNoticias);
        new FragmentNoticias.ObtenerNoticias(festival,listaNoticias).execute();

        return rootView;

    }




    // **************** GETTER AND SETTERS ********************


    public ArrayList<Noticia> getListaNoticiasFestival() {
        return listaNoticiasFestival;
    }

    public void setListaNoticiasFestival(ArrayList<Noticia> listaNoticiasFestival) {
        this.listaNoticiasFestival = listaNoticiasFestival;
    }

    public Festival getFestivalNoticas() {
        return festivalNoticas;
    }

    public void setFestivalNoticas(Festival festivalNoticas) {
        this.festivalNoticas = festivalNoticas;
    }

    public static Context getConexto() {
        return conexto;
    }

    public static void setConexto(Context conexto) {
        FragmentNoticias.conexto = conexto;
    }








    /**
     * Hilo que se ejecuta en segundo plano para traer las noticias del festival de la BD
     */
    private  class ObtenerNoticias extends AsyncTask<Void,Void,ArrayList<Noticia>>{
        private Festival festival;
        private ListView listViewNoticias;


        public ObtenerNoticias(Festival festival,ListView listViewNoticias){
            this.festival = festival;
            this.listViewNoticias = listViewNoticias;
        }



        @Override
        protected ArrayList<Noticia> doInBackground(Void... voids) {
            Comando comando = new Comando();
            comando.setOrden("noticiasFest");
            comando.getArgumentos().add(festival.getIdFestival());
            ArrayList<Noticia> listaNoticias = new ArrayList<Noticia>();
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
                listaNoticias = (ArrayList<Noticia>) comando.getArgumentos().get(0);
            } catch (IOException e) {
                Toast.makeText(conexto,"Problema con la conexion del Servidor",Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(conexto,"Problema con la conexion del Servidor",Toast.LENGTH_LONG).show();
            }

            return listaNoticias;
        }

        @Override
        protected void onPostExecute(ArrayList<Noticia> lista) {
            if(lista.size()==0){
                Toast.makeText(conexto,"No existen noticias para el Festival "+festival.getNombre(),Toast.LENGTH_LONG).show();
            }else{
                AdaptadorNoticiaBasico adaptador = new AdaptadorNoticiaBasico(conexto,lista,listViewNoticias);
                listViewNoticias.setAdapter(adaptador);
            }
        }
    }


}
