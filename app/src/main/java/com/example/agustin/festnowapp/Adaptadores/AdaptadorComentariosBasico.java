package com.example.agustin.festnowapp.Adaptadores;

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

import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.UtilFechas;

import java.util.ArrayList;

import modelos.Comentario;

/**
 * @author Agustin/Adrian
 * Adaptador para adaptar los items de los comentarios de la lista de detalles del festival
 */
public class AdaptadorComentariosBasico extends BaseAdapter {
    private Context contexto;
    private ArrayList<Comentario> listaComentarios;
    private ListView listaComentariosPantalla;


    public AdaptadorComentariosBasico(Context contexto, ArrayList<Comentario> listaComentarios, ListView listaComentariosPantalla) {
        this.contexto = contexto;
        this.listaComentarios = listaComentarios;
        this.listaComentariosPantalla = listaComentariosPantalla;
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
        convertView = LayoutInflater.from(contexto).inflate(R.layout.item_comentario,null);

        TextView nombreUsuario = (TextView)convertView.findViewById(R.id.usuarioComenBasico);
        ImageView imagenUsuario = (ImageView)convertView.findViewById(R.id.imagenPerfilComenBasico);
        TextView fechaComentario = (TextView)convertView.findViewById(R.id.fechaComenBasico);
        TextView textoComentario = (TextView)convertView.findViewById(R.id.comentarioComenBasico);

        //nombre Usuario
        nombreUsuario.setText(comentario.getCliente().getNombre());
        //imagenUsuario
        if(comentario.getCliente().getNombreFoto().equals("default")){
           imagenUsuario.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap imagenByte = BitmapFactory.decodeByteArray(comentario.getCliente().getFotoByte(),0,comentario.getCliente().getFotoByte().length);
            imagenUsuario.setImageBitmap(imagenByte);
        }
        //fecha del comentario
        fechaComentario.setText(UtilFechas.procesarFechaNoticia(comentario.getFechaComentario()));
        //texto del comentario
        textoComentario.setText(comentario.getTextoComentario());

        return convertView;
    }
}
