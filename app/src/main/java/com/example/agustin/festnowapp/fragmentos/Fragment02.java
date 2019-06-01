package com.example.agustin.festnowapp.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.agustin.festnowapp.R;


public class Fragment02 extends Fragment{
    RatingBar ratingBarFesti;
    RatingBar ratingBarUsuario;
    Button btnValoraFesti;
    Button btnValoraUsuario;
    Button enviarComent;
    Button listarComent;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragment02, container, false);
        ratingBarFesti = (RatingBar) view.findViewById(R.id.ratingValFestival);
        ratingBarUsuario=(RatingBar) view.findViewById(R.id.ratingValUsuario);

        btnValoraFesti = (Button) view.findViewById(R.id.btnValFestival);
        btnValoraUsuario = (Button) view.findViewById(R.id.btnRatingValUsuario);
        enviarComent = (Button) view.findViewById(R.id.btnEnviarComentario);
        listarComent = (Button) view.findViewById(R.id.btnListarComentarios);


        //ejecucion valoracion festi
        btnValoraFesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float val= (int) ratingBarFesti.getRating();
                Toast.makeText(getContext(),"La valoracion es: "+val,Toast.LENGTH_LONG).show();
            }
        });

        //ejecucion valoracion usu
        btnValoraUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float val2=(int) ratingBarUsuario.getRating();
                Toast.makeText(getContext(),"La valoracion es: "+val2,Toast.LENGTH_LONG).show();
            }
        });

        //ejecucion enviar comentario
        enviarComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //recoger texto de la caja de texto y enviarlo
            }
        });

        listarComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //hacer la llamada a la clase comentarios
            }
        });



        return view;
    }



}
