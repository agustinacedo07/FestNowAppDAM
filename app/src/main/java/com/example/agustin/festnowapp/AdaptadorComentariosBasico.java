package com.example.agustin.festnowapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agustin.festnowapp.Util.UtilFechas;

import java.util.ArrayList;

import modelos.Comentario;

public class AdaptadorComentariosBasico extends BaseAdapter {
    private Context contexto;
    private ArrayList<Comentario> listaComentarios;
    private ListView listaComentariosPantalla;
    private FragmentComentarios pantallaComentarios;


    public AdaptadorComentariosBasico(Context contexto, ArrayList<Comentario> listaComentarios, ListView listaComentariosPantalla, FragmentComentarios pantallaComentarios) {
        this.contexto = contexto;
        this.listaComentarios = listaComentarios;
        this.listaComentariosPantalla = listaComentariosPantalla;
        this.pantallaComentarios = pantallaComentarios;
    }

    @Override
    public int getCount() {
        return listaComentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaComentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comentario comentario = (Comentario) getItem(position);
        convertView = LayoutInflater.from(contexto).inflate(R.layout.itemcomentarios,null);

        TextView nombreUsuario = (TextView)convertView.findViewById(R.id.usuarioComenBasico);
        ImageView imagenUsuario = (ImageView)convertView.findViewById(R.id.imagenPerfilComenBasico);
        TextView fechaComentario = (TextView)convertView.findViewById(R.id.fechaComenBasico);
        TextView textoComentario = (TextView)convertView.findViewById(R.id.comentarioComenBasico);

        nombreUsuario.setText(comentario.getCliente().getNombre());
        if(comentario.getCliente().getNombreFoto().equals("default")){
           imagenUsuario.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap imagenByte = BitmapFactory.decodeByteArray(comentario.getCliente().getFotoByte(),0,comentario.getCliente().getFotoByte().length);
            imagenUsuario.setImageBitmap(imagenByte);
        }


        fechaComentario.setText(UtilFechas.procesarFechaNoticia(comentario.getFechaComentario()));

        textoComentario.setText(comentario.getTextoComentario());

        return convertView;
    }
}
