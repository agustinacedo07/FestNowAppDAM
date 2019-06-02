package com.example.agustin.festnowapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.se.omapi.Session;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agustin.festnowapp.Util.SesionServer;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Comando;
import modelos.Festival;

public class FestivalesSeguidos extends AppCompatActivity {

    ListView listaFestivales;
    Button btnVolverPantallaPrincipal;
    AdaptadorFestivalesFollow adaptadorFestivalesFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festivales_seguidos);

        listaFestivales = (ListView) findViewById(R.id.listaFestisSeguidos);
        btnVolverPantallaPrincipal = (Button)findViewById(R.id.btnVolverPantalla);



        new ListarFestivalesFollow(SesionServer.clienteAplicacion.getIdCliente(),listaFestivales,this).execute();

        //funcionalidad de volver a pantalla principal
        btnVolverPantallaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaPrincipal = new Intent(getApplicationContext(),PantallaPrincipal.class);
                startActivity(pantallaPrincipal);
            }
        });
    }

    private class ListarFestivalesFollow extends AsyncTask<Integer,Void,Object>{
        private int idCliente;
        private ListView listaFestivalesFollor;
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
                SesionServer.flujoSalidaObjetos.writeObject(comando);
                comando = (Comando) SesionServer.flujoEntradaObjetos.readObject();

            } catch (IOException e) {
                return  null;
            } catch (ClassNotFoundException e) {
                return null;
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
                adaptadorFestivalesFollow = new AdaptadorFestivalesFollow(getApplicationContext(),listaFestivalesFollow,listaFestivalesFollor,pantallaFestivalesSeguidos);
                listaFestivales.setAdapter(adaptadorFestivalesFollow);

            }
        }
    }


    public void lanzarDetalleFestival(Festival festival){
        try{
            Intent pantallaDetalleFestival = new Intent(getApplicationContext(),PantallaPrincipalDelFestival.class);
            pantallaDetalleFestival.putExtra("festival",festival);
            startActivity(pantallaDetalleFestival);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
