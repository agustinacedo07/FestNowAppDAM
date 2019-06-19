package com.example.agustin.festnowapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;

import modelos.Comando;
import modelos.Festival;

public class FragmentValoraciones extends Fragment {
    static Context contexto;


    public static FragmentValoraciones newInstance(Context contextoPadre) {
        contexto = contextoPadre;

        Bundle args = new Bundle();



        FragmentValoraciones fragment = new FragmentValoraciones();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.pantalla_valoraciones,container,false);


        final RatingBar ratingValoracion = (RatingBar)viewRoot.findViewById(R.id.valorafestival);
        final EditText comentario = (EditText)viewRoot.findViewById(R.id.cajaComen);
        Button btnComentar = (Button)viewRoot.findViewById(R.id.btnEnviarComentario2);



        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 float valoracionUsuario = ratingValoracion.getRating();
                 String txtComentario = comentario.getText().toString();
                new InsertarComentario(txtComentario,valoracionUsuario).execute();
                Intent pantallaFestival = new Intent(contexto,FestivalesSeguidos.class);
                startActivity(pantallaFestival);
            }
        });


        return viewRoot;

    }


    public static class InsertarComentario extends AsyncTask<Void,Void,Boolean>{
        private String textoComentario;
        private float valoracionUsuario;

        public InsertarComentario(String textoComentario,float valoracionUsuario){
            this.textoComentario = textoComentario;
            this.valoracionUsuario = valoracionUsuario;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean operacion = false;
            Comando comando = new Comando();
            comando.setOrden("insertValoracion");
            comando.getArgumentos().add(SesionUserServer.clienteAplicacion.getIdCliente());
            comando.getArgumentos().add(PantallaPrincipalDelFestival.festival.getIdFestival());
            comando.getArgumentos().add(textoComentario);
            comando.getArgumentos().add(valoracionUsuario);
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                operacion = SesionUserServer.flujoDatosEntradaUser.readBoolean();
            } catch (IOException e) {
                return false;
            }
            return operacion;
        }

        @Override
        protected void onPostExecute(Boolean operacion) {
            if(!operacion){
                Toast.makeText(contexto,"Problema con la conexión al Servidor",Toast.LENGTH_LONG).show();
            }
        }
    }
}
