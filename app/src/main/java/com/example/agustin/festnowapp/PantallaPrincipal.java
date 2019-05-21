package com.example.agustin.festnowapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import modelos.Comando;
import modelos.Festival;


public class PantallaPrincipal extends AppCompatActivity {

    private AdaptadorFestivalesUser adaptadorFestivalesUser;
    private ListView listaFestivales;
    private ArrayList<Festival> arrayFestivales;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        listaFestivales = (ListView)findViewById(R.id.listFestivalesPantallaPral);

        new ListaFestivalesUser(Logueo.clienteAplicacion.getIdCliente(),listaFestivales).execute();




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
                Logueo.flujoSalidaObjetos.writeObject(comando);
                comando = (Comando) Logueo.flujoEntradaObjetos.readObject();
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





}
