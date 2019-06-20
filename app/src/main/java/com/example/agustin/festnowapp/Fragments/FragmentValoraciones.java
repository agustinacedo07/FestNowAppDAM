package com.example.agustin.festnowapp.Fragments;

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

import com.example.agustin.festnowapp.Activitys.FestivalesSeguidos;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;

import modelos.Comando;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase que hace referencia a las valoraciones y comentarios que haga el usuario sobre el festival
 * Una vez que comente esta pantalla desaparecerá puesto que solo puede realizar un comentario y una valoracion por festival
 */

public class FragmentValoraciones extends Fragment {
    static Context contexto;
    private RatingBar ratingValoraciones;
    private EditText comentario;
    private Button btnComentar;


    public static FragmentValoraciones newInstance(Festival festival, Context contextoPadre) {
        contexto = contextoPadre;
        Bundle args = new Bundle();
        args.putSerializable("festival",festival);
        FragmentValoraciones fragment = new FragmentValoraciones();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.pagina_valoraciones,container,false);
        final Festival festival = (Festival) getArguments().get("festival");

        ratingValoraciones = (RatingBar)viewRoot.findViewById(R.id.valorafestival);
        comentario = (EditText)viewRoot.findViewById(R.id.cajaComen);
        btnComentar = (Button)viewRoot.findViewById(R.id.btnEnviarComentario2);



        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 float valoracionUsuario = ratingValoraciones.getRating();
                 String txtComentario = comentario.getText().toString();
                 if(ratingValoraciones.getRating()==0 || txtComentario.isEmpty()){
                     Toast.makeText(contexto,"Valoración o campo de comentario vacío",Toast.LENGTH_LONG).show();
                 }else{
                     new InsertarComentario(txtComentario,valoracionUsuario,festival).execute();
                     Intent pantallaFestival = new Intent(contexto,FestivalesSeguidos.class);
                     startActivity(pantallaFestival);
                 }
            }
        });

        return viewRoot;

    }

    //**************  GETTER AND SETTERS ************


    public static Context getContexto() {
        return contexto;
    }

    public static void setContexto(Context contexto) {
        FragmentValoraciones.contexto = contexto;
    }

    public RatingBar getRatingValoraciones() {
        return ratingValoraciones;
    }

    public void setRatingValoraciones(RatingBar ratingValoraciones) {
        this.ratingValoraciones = ratingValoraciones;
    }

    public EditText getComentario() {
        return comentario;
    }

    public void setComentario(EditText comentario) {
        this.comentario = comentario;
    }

    public Button getBtnComentar() {
        return btnComentar;
    }

    public void setBtnComentar(Button btnComentar) {
        this.btnComentar = btnComentar;
    }


    /**
     * Hilo que realiza en segundo plano la inserción y valoración del comentario del usuario en la BD
     */
    private  class InsertarComentario extends AsyncTask<Void,Void,Boolean>{
        private String textoComentario;
        private float valoracionUsuario;
        private Festival festival;

        public InsertarComentario(String textoComentario,float valoracionUsuario,Festival festival){
            this.textoComentario = textoComentario;
            this.valoracionUsuario = valoracionUsuario;
            this.festival = festival;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean operacion = false;
            Comando comando = new Comando();
            comando.setOrden("insertValoracion");
            comando.getArgumentos().add(SesionUserServer.clienteAplicacion.getIdCliente());
            comando.getArgumentos().add(festival.getIdFestival());
            comando.getArgumentos().add(textoComentario);
            comando.getArgumentos().add(valoracionUsuario);
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                operacion = SesionUserServer.flujoDatosEntradaUser.readBoolean();
                operacion = true;
            } catch (IOException e) {
                Toast.makeText(contexto,"Problemas de conexion",Toast.LENGTH_LONG).show();
            }
            return operacion;
        }

        @Override
        protected void onPostExecute(Boolean operacion) {
            if(!operacion){
                Toast.makeText(contexto,"No ha podido realizarse el Comentario",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(contexto,"Comentario realizado con éxito",Toast.LENGTH_LONG).show();

            }
        }
    }
}
