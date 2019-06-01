package com.example.agustin.festnowapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.agustin.festnowapp.Util.SesionServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import modelos.Comando;
import modelos.Festival;


public class PantallaPrincipal extends AppCompatActivity {

    private AdaptadorFestivalesUser adaptadorFestivalesUser;
    private ListView listaFestivales;


    private ToggleButton btnCoste,btnFecha,btnPopularidad;
    private ImageButton btnFestivalesSeguidos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        listaFestivales = (ListView)findViewById(R.id.listFestivalesPantallaPral);

        new ListaFestivalesUser(SesionServer.clienteAplicacion.getIdCliente(),listaFestivales).execute();

        btnCoste = (ToggleButton)findViewById(R.id.btnCoste);
        btnFecha = (ToggleButton)findViewById(R.id.btnFecha);
        btnPopularidad = (ToggleButton)findViewById(R.id.btnPopularidad);
        btnFestivalesSeguidos = (ImageButton)findViewById(R.id.btnFestivalesSeguidos);


        //funcionalidad de festivales seguidos
        btnFestivalesSeguidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantFestisSeguidos = new Intent(getApplicationContext(), FestivalesSeguidos.class);
                startActivity(pantFestisSeguidos);
            }
        });




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


        btnPopularidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPopularidad.setBackgroundColor(Color.RED);
                btnCoste.setBackgroundColor(Color.BLUE);
                btnFecha.setBackgroundColor(Color.BLUE);
                //ordenar por popularidad primeros mas populares y despues menos populares
                try {
                    ordenarPopularidad();
                }catch(Exception e){
                    e.printStackTrace();
                }
                adaptadorFestivalesUser.notifyDataSetChanged();
            }
        });



    }

    public void IrPantallaFestisSeguidos(View view) {

    }


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
                SesionServer.flujoSalidaObjetos.writeObject(comando);
                comando = (Comando) SesionServer.flujoEntradaObjetos.readObject();
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
                ArrayList<Festival>listaFestivalesUser = (ArrayList<Festival>) listaFestivalesBD;
                adaptadorFestivalesUser = new AdaptadorFestivalesUser(getApplicationContext(),listaFestivalesUser,listaFestivalesUsuario);
                listaFestivales.setAdapter(adaptadorFestivalesUser);

            }
        }
    }


    public void ordenarCoste(){
        Collections.sort(AdaptadorFestivalesUser.getListaFestivales(), new Comparator<Festival>() {
            @Override
            public int compare(Festival o1, Festival o2) {
              return Double.compare(o1.getPrecioMedio(),o2.getPrecioMedio());

            }
        });
    }

    public void ordenarPopularidad(){
        Collections.sort(AdaptadorFestivalesUser.getListaFestivales(), new Comparator<Festival>() {
            @Override
            public int compare(Festival o1, Festival o2) {
                return Double.compare(o2.getValoracion(),o1.getValoracion());
            }
        });

    }

    public void ordenarFechas(){
        Collections.sort(AdaptadorFestivalesUser.getListaFestivales(), new Comparator<Festival>() {
            @Override
            public int compare(Festival o1, Festival o2) {
               return  o1.getFechaInicio().compareTo(o2.getFechaInicio());
            }
        });

    }





}
