package com.example.agustin.festnowapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import modelos.Artista;
import modelos.Festival;

public class AdaptadorFestivalesFollow extends BaseAdapter {
    private Context contexto;
    private ArrayList<Festival>listaFestivalesSeguidos;
    private ListView listaPantallaFestivales;


    public AdaptadorFestivalesFollow(Context contexto, ArrayList<Festival> listaFestivalesSeguidos, ListView listaPantallaFestivales) {
        this.contexto = contexto;
        this.listaFestivalesSeguidos = listaFestivalesSeguidos;
        this.listaPantallaFestivales = listaPantallaFestivales;
    }



    @Override
    public int getCount() {
        return listaFestivalesSeguidos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFestivalesSeguidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Festival festival = (Festival) getItem(position);

        convertView = LayoutInflater.from(contexto).inflate(R.layout.itemfestivalesfollow,null);
        //adaptar componentes de item
        ImageView imagenFoto = (ImageView)convertView.findViewById(R.id.fotoLogo);
        TextView titulo = (TextView)convertView.findViewById(R.id.titulo);
        TextView fechaInicio = (TextView)convertView.findViewById(R.id.fechainicio);
        TextView fechaFin = (TextView)convertView.findViewById(R.id.fechafin);
        TextView ciudad = (TextView)convertView.findViewById(R.id.ciudad);
        TextView artistas = (TextView)convertView.findViewById(R.id.artistas);
        TextView precioMedio = (TextView)convertView.findViewById(R.id.precioMedio);
        TextView valoracion = (TextView)convertView.findViewById(R.id.valoracion);



        //modificar los elementos por los valores del festival
        //foto
        if(festival.getNombreFotoPral().equals("default")){
            imagenFoto.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(festival.getImagenLogo(),0,festival.getImagenLogo().length);
            imagenFoto.setImageBitmap(bitmap);
        }

        titulo.setText(festival.getNombre());
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        fechaInicio.setText(formatoFecha.format(festival.getFechaInicio()));
        fechaFin.setText(formatoFecha.format(festival.getFechaFin()));
        ciudad.setText(festival.getLocalidad());
        precioMedio.setText(Double.toString(festival.getPrecioMedio())+" €");
        ArrayList<Artista> listaArtistas = festival.getListaArtistas();
        String txtArtistas = "";
        if(listaArtistas.size()==0){
            txtArtistas = "Artístas sin confirmar";
        }else{
            for(int i=0;i<listaArtistas.size();i++){
                txtArtistas += listaArtistas.get(i).getNombreArtista()+"  ";
            }

        }

        artistas.setText(txtArtistas);


        //recogida de valoraciones
        String mensajeValoraciones;
        double valoracionFestival = festival.getValoracion();
        int numeroValoraciones = festival.getNumValoraciones();
        if(valoracionFestival==-1){
            mensajeValoraciones = "Aún no tiene valoraciones";
        }else{
            mensajeValoraciones = "Valoración : "+valoracionFestival+" / "+numeroValoraciones;

        }

        valoracion.setText(mensajeValoraciones);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nos llevaria a la otra pantalla a la que le pasaremos el festival completo
                Toast.makeText(contexto,"Ha pulsado el Festival "+festival.getNombre(),Toast.LENGTH_LONG).show();
            }
        });


        return  convertView;
    }
}
