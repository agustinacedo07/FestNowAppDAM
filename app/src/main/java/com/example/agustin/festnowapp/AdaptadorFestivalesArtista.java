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

import modelos.Artista;
import modelos.Concierto;

public class AdaptadorFestivalesArtista extends BaseAdapter {
    private Context contexto;
    private ArrayList <modelos.Festival> listaFestivalesArtista;
    private ListView listaPantallaFestivalesArtista;
    private  PantallaDetalleArtista pantallaGeneralArista;
    private Artista artista;

    public AdaptadorFestivalesArtista(Context contexto, ArrayList<modelos.Festival> listaFestivalesArtista, ListView listaPantallaFestivalesArtista, PantallaDetalleArtista pantallaGeneralArista, Artista artista) {
        this.contexto = contexto;
        this.listaFestivalesArtista = listaFestivalesArtista;
        this.listaPantallaFestivalesArtista = listaPantallaFestivalesArtista;
        this.pantallaGeneralArista = pantallaGeneralArista;
        this.artista = artista;
    }

    @Override
    public int getCount() {
        return listaFestivalesArtista.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFestivalesArtista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            final modelos.Festival festival = (modelos.Festival) getItem(position);


            convertView = LayoutInflater.from(contexto).inflate(R.layout.item_fest_artista,null);

            //iniciamos los componentes
            ImageView imagenLogoFestival = (ImageView)convertView.findViewById(R.id.imagenLogoListadoArtista);
            TextView txtNombreArtista = (TextView)convertView.findViewById(R.id.txtNombreFestivalArtista);
            TextView txtFechaConcierto = (TextView)convertView.findViewById(R.id.txtFechaConciertoFestArtista);

            //imagen del logo del festival
            if(!festival.getNombreFotoLogo().equals("default")){
                Bitmap imagenLogoFestivalByte = BitmapFactory.decodeByteArray(festival.getImagenLogo(),0,festival.getImagenLogo().length);
                imagenLogoFestival.setImageBitmap(imagenLogoFestivalByte);

            }else{
                imagenLogoFestival.setImageResource(R.mipmap.logo2);
            }

            //nombre del artista
            txtNombreArtista.setText(artista.getNombreArtista());

            //fecha del concierto
            String fechaProcesada = "Fecha sin determinar";
            for(int i=0;i<artista.getListaConciertos().size();i++){
                Concierto concierto = artista.getListaConciertos().get(i);
                if(concierto.getFestival().equals(festival)){
                    fechaProcesada = UtilFechas.procesarFechaConcierto(concierto.getFechaConcierto());
                    break;
                }
            }

            txtFechaConcierto.setText(fechaProcesada);





            return convertView;



    }
}
