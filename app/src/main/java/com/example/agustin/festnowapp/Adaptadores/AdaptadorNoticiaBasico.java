package com.example.agustin.festnowapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import modelos.Noticia;

/**
 * @author Agustin/Adrian
 * Adaptador para incorporar los instems de noticias a la pantalla de detalle festival, en su apartado de noticias
 */
public class AdaptadorNoticiaBasico extends BaseAdapter {
    private Context conexto;
    private ArrayList<Noticia> listaNoticias;
    private ListView listaNoticiasPantalla;



    public AdaptadorNoticiaBasico(Context conexto, ArrayList<Noticia> listaNoticias, ListView listaNoticiasPantalla) {
        this.conexto = conexto;
        this.listaNoticias = listaNoticias;
        this.listaNoticiasPantalla = listaNoticiasPantalla;
    }

    @Override
    public int getCount() {
        return listaNoticias.size();
    }

    @Override
    public Object getItem(int position) {
        return listaNoticias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Noticia noticia = (Noticia) getItem(position);
        convertView = LayoutInflater.from(conexto).inflate(R.layout.item_noticia,null);

        //elementos
        TextView fecha = convertView.findViewById(R.id.fechaNoticiaBasico);
        ImageView imagenNoticia = convertView.findViewById(R.id.imagenNoticiaBasico);
        TextView titular = convertView.findViewById(R.id.titularNoticiaBasico);
        String fechaProcesada = UtilFechas.procesarFechaNoticia(noticia.getFechaNoticia());


        //fecha
        fecha.setText(fechaProcesada);

        //imagenNoticia
        if(!noticia.getNombreFoto().equals("default")){
            Bitmap imagenNoticiaByte = BitmapFactory.decodeByteArray(noticia.getFotoNoticia(),0,noticia.getFotoNoticia().length);
            imagenNoticia.setImageBitmap(imagenNoticiaByte);
        }else{
            imagenNoticia.setImageResource(R.mipmap.logo2);
        }
        //titularNoticia
        titular.setText(noticia.getTitular());
        //enlaza al pulsar el item con la página web de la noticia
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webNoticia = new Intent(Intent.ACTION_VIEW, Uri.parse(noticia.getEnlaceNoticia()));
                conexto.startActivity(webNoticia);
            }
        });

        return convertView;
    }
}
