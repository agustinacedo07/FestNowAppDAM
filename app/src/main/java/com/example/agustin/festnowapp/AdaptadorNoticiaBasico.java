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

import modelos.Noticia;

public class AdaptadorNoticiaBasico extends BaseAdapter {
    private Context conexto;
    private ArrayList<Noticia> listaNoticias;
    private ListView listaNoticiasPantalla;
    private FragmentNoticias pantallaNoticias;



    public AdaptadorNoticiaBasico(Context conexto, ArrayList<Noticia> listaNoticias, ListView listaNoticiasPantalla, FragmentNoticias pantallaNoticias) {
        this.conexto = conexto;
        this.listaNoticias = listaNoticias;
        this.listaNoticiasPantalla = listaNoticiasPantalla;
        this.pantallaNoticias = pantallaNoticias;
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
        convertView = LayoutInflater.from(conexto).inflate(R.layout.itemnoticias,null);

        //elementos
        TextView fecha = convertView.findViewById(R.id.fechaNoticiaBasico);
        ImageView imagenNoticia = convertView.findViewById(R.id.imagenNoticiaBasico);
        TextView titular = convertView.findViewById(R.id.titularNoticiaBasico);

        String fechaProcesada = UtilFechas.procesarFechaNoticia(noticia.getFechaNoticia());
        fecha.setText(fechaProcesada);

        if(!noticia.getNombreFoto().equals("default")){
            Bitmap imagenNoticiaByte = BitmapFactory.decodeByteArray(noticia.getFotoNoticia(),0,noticia.getFotoNoticia().length);
            imagenNoticia.setImageBitmap(imagenNoticiaByte);
        }else{
            imagenNoticia.setImageResource(R.mipmap.logo2);
        }

        titular.setText(noticia.getTitular());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantallaNoticias.lanzarPaginaNoticia(noticia.getEnlaceNoticia());
            }
        });



        return convertView;
    }
}
