package com.example.agustin.festnowapp.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.agustin.festnowapp.Adaptadores.AdaptadorFestivalesFollow;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Comando;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase que muestra los festivales que sigue el usuario
 */
public class FestivalesSeguidos extends AppCompatActivity {

    private ListView listaFestivales;
    private Button btnVolverPantallaPrincipal;
    private AdaptadorFestivalesFollow adaptadorFestivalesFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_festivales_seguidos);

        listaFestivales = (ListView) findViewById(R.id.listaFestisSeguidos);
        btnVolverPantallaPrincipal = (Button)findViewById(R.id.btnVolverPantalla);

        //traemos un listado de todos los festivales que sigue el usuario
        new ListarFestivalesFollow(SesionUserServer.clienteAplicacion.getIdCliente(),listaFestivales,this).execute();


        //funcionalidad de volver a pantalla principal
        btnVolverPantallaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaPrincipal = new Intent(getApplicationContext(),PantallaPrincipal.class);
                startActivity(pantallaPrincipal);
            }
        });
    }


    /**
     * Hilo que se ejecuta en segundo plano, para traer de la BD los festivales que sigue el usuario
     */
    private class ListarFestivalesFollow extends AsyncTask<Integer,Void,Object>{
        private int idCliente;
        private ListView listaFestivalesFollor;
        //para poder instanciar en el adaptador un click a el item de este festival
        private FestivalesSeguidos pantallaFestivalesSeguidos;

        public ListarFestivalesFollow(int idCliente,ListView listaFestivales,FestivalesSeguidos pantallaFestivalesSeguidos){
            this.idCliente = idCliente;
            this.listaFestivalesFollor = listaFestivales;
            this.pantallaFestivalesSeguidos = pantallaFestivalesSeguidos;
        }

        @Override
        protected Object doInBackground(Integer... integers) {
            modelos.Comando comando = new Comando();
            comando.setOrden("listFestUserFollow");
            comando.getArgumentos().add(idCliente);

            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
            } catch (IOException e) {
               Toast.makeText(getApplicationContext(),"Problema con la conexion al Servidor",Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(getApplicationContext(),"Problema con la conexion al Servidor",Toast.LENGTH_LONG).show();
            }

            return  comando.getArgumentos().get(0);

        }

        @Override
        protected void onPostExecute(Object listaFestivalesBD) {
            if(listaFestivalesBD==null){
                Toast.makeText(getApplicationContext(),"Ha habido un problema con la comunicación",Toast.LENGTH_LONG).show();
            }else{
                ArrayList<Festival> listaFestivalesFollow = (ArrayList<Festival>) listaFestivalesBD;
                //adaptar el festival
                adaptadorFestivalesFollow = new AdaptadorFestivalesFollow(getApplicationContext(),listaFestivalesFollow,listaFestivalesFollor);
                listaFestivales.setAdapter(adaptadorFestivalesFollow);

            }
        }
    }

}
