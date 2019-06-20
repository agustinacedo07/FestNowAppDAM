package com.example.agustin.festnowapp.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.agustin.festnowapp.Adaptadores.AdaptadorFestivalesUser;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import modelos.Comando;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase principal que muestra los festivales disponibles en la aplicación que no sigue el usuario y que no hayan
 * sido realizado aún (con fecha posterior a la actual)
 */
public class PantallaPrincipal extends AppCompatActivity {
    //elementos de pantalla
    private AdaptadorFestivalesUser adaptadorFestivalesUser;
    private ListView listaFestivales;
    private ToggleButton btnCoste,btnFecha,btnPopularidad;
    private ImageButton btnFestivalesSeguidos,btnPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_principal);

        //inicialización de elementos de la pantalla
        listaFestivales = (ListView)findViewById(R.id.listFestivalesPantallaPral);
        btnCoste = (ToggleButton)findViewById(R.id.btnCoste);
        btnFecha = (ToggleButton)findViewById(R.id.btnFecha);
        btnPopularidad = (ToggleButton)findViewById(R.id.btnPopularidad);
        btnFestivalesSeguidos = (ImageButton)findViewById(R.id.btnFestivalesSeguidos);
        btnPerfil = (ImageButton)findViewById(R.id.btnPerfil);

        //realiza una consulta al servidor, para traerse los festivales a mostrar
        new ListaFestivalesUser(SesionUserServer.clienteAplicacion.getIdCliente(),listaFestivales).execute();


        //funcionalidad de festivales seguidos
        /**
         * Lanza una nueva pantalla con los festivales que sigue el usuario
         */
        btnFestivalesSeguidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantFestisSeguidos = new Intent(getApplicationContext(), FestivalesSeguidos.class);
                startActivity(pantFestisSeguidos);
            }
        });

        //************ BOTONES DE ORDENACIÓN DE LISTADO DE FESTIVALES ***************
        //ordenación de coste de menos a mayor
        btnCoste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    btnCoste.setBackgroundColor(Color.RED);
                    btnFecha.setBackgroundColor(Color.BLUE);
                    btnPopularidad.setBackgroundColor(Color.BLUE);
                    //ordenar por coste mas bajo a mayor
                    ordenarCoste();
                    adaptadorFestivalesUser.notifyDataSetChanged();

            }
        });

        //ordenación por fechas de más recientes a posteriores
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFecha.setBackgroundColor(Color.RED);
                btnCoste.setBackgroundColor(Color.BLUE);
                btnPopularidad.setBackgroundColor(Color.BLUE);
                //ordenar por fecha, primero mas cercanos y despues más lejanos
                ordenarFechas();
                adaptadorFestivalesUser.notifyDataSetChanged();

            }
        });

        //ordenacion por la valoracion del festival de mas populares a menos populares
        btnPopularidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPopularidad.setBackgroundColor(Color.RED);
                btnCoste.setBackgroundColor(Color.BLUE);
                btnFecha.setBackgroundColor(Color.BLUE);
                //ordenar por popularidad primeros mas populares y despues menos populares
                ordenarPopularidad();
                adaptadorFestivalesUser.notifyDataSetChanged();
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent pantallaPerfil = new Intent(getApplicationContext(),PantallaPerfil.class);
                    startActivity(pantallaPerfil);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });



    }



    //***************** METODOS DE ORDENACIÓN **********************
    //Métodos de ordenación de los festivales

    /**
     * Ordena los festivales de menos coste a mayor coste
     */
    public void ordenarCoste(){
        Collections.sort(AdaptadorFestivalesUser.getListaFestivales(), new Comparator<Festival>() {
            @Override
            public int compare(Festival o1, Festival o2) {
              return Double.compare(o1.getPrecioMedio(),o2.getPrecioMedio());

            }
        });
    }

    /**
     * Ordena los festivales de mayor popularidad a menor
     */
    public void ordenarPopularidad(){
        Collections.sort(AdaptadorFestivalesUser.getListaFestivales(), new Comparator<Festival>() {
            @Override
            public int compare(Festival o1, Festival o2) {
                return Double.compare(o2.getValoracion(),o1.getValoracion());
            }
        });

    }

    /**
     * Ordena los festivales de más reciente a posteriores
     */
    public void ordenarFechas(){
        Collections.sort(AdaptadorFestivalesUser.getListaFestivales(), new Comparator<Festival>() {
            @Override
            public int compare(Festival o1, Festival o2) {
               return  o1.getFechaInicio().compareTo(o2.getFechaInicio());
            }
        });

    }


    /**
     * Hilo que se ejecuta en segundo plano, para traer los festivales que sigue el usuario
     */
    private  class ListaFestivalesUser extends AsyncTask<Integer,Void,Object>{
        int idCliente;
        ListView listaFestivalesUsuario;

        public ListaFestivalesUser(int idCliente,ListView listaFestivalesUsuario) {
            this.idCliente = idCliente;
            this.listaFestivalesUsuario = listaFestivalesUsuario;
        }


        @Override
        protected Object doInBackground(Integer... integers) {
            Comando comando = new Comando();
            comando.setOrden("listFestUser");
            comando.getArgumentos().add(idCliente);

            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
            } catch (IOException e) {
                return null;
            } catch (ClassNotFoundException e) {
                return null;
            }

            return comando.getArgumentos().get(0);
        }

        @Override
        protected void onPostExecute(Object listaFestivalesBD) {
            if(listaFestivalesBD==null){
                Toast.makeText(getApplicationContext(),"Ha habido un problema al conectarse con el Servidor",Toast.LENGTH_LONG).show();
            }else{
                //una vez se traiga los festivales a través del adaptador los muestra en pantalla
                ArrayList<Festival>listaFestivalesUser = (ArrayList<Festival>) listaFestivalesBD;
                adaptadorFestivalesUser = new AdaptadorFestivalesUser(getApplicationContext(),listaFestivalesUser,listaFestivalesUsuario);
                listaFestivales.setAdapter(adaptadorFestivalesUser);

            }
        }
    }


}
