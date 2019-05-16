package com.example.agustin.festnowapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import modelos.Artista;
import modelos.Festival;

public class AdaptadorFestivalesUser extends BaseAdapter{
    private Context contexto;
    private ArrayList<Festival> listaFestivales;

    public AdaptadorFestivalesUser(Context contexto, ArrayList<Festival> listaFestivales) {
        this.contexto = contexto;
        this.listaFestivales = listaFestivales;
    }

    @Override
    public int getCount() {
        return listaFestivales.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFestivales.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Festival festival = (Festival) getItem(position);


        convertView = LayoutInflater.from(contexto).inflate(R.layout.huecolistafesti,null);
        ImageView imagenFoto = (ImageView)convertView.findViewById(R.id.fotoFestival);
        TextView titulo = (TextView)convertView.findViewById(R.id.titulo);
        TextView fechaInicio = (TextView)convertView.findViewById(R.id.fechainicio);
        TextView fechaFin = (TextView)convertView.findViewById(R.id.fechafin);
        TextView ciudad = (TextView)convertView.findViewById(R.id.ciudad);
        TextView artistas = (TextView)convertView.findViewById(R.id.artistas);

        if(festival.getNombreFotoPral().equals("default")){
            imagenFoto.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(festival.getImagenPralByte(),0,festival.getImagenPralByte().length);
            imagenFoto.setImageBitmap(bitmap);
        }

        titulo.setText(festival.getNombre());
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        fechaInicio.setText(formatoFecha.format(festival.getFechaInicio()));
        fechaFin.setText(formatoFecha.format(festival.getFechaFin()));
        ciudad.setText(festival.getLocalidad());
        ArrayList<Artista> listaArtistas = new ArrayList<>();
        String txtArtistas = "";

            for(int i=0;i<listaArtistas.size();i++){
                txtArtistas += listaArtistas.get(i).getNombreArtista()+" ";
            }

            artistas.setText(txtArtistas);


        return convertView;
    }
}
