package com.example.agustin.festnowapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agustin.festnowapp.Activitys.PantallaDetalleArtista;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.UtilFechas;

import java.util.ArrayList;

import modelos.Artista;
import modelos.Concierto;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Adaptador para adaptar los artistas de un festival en su pantalla de Detalle
 */
public class AdaptadorArtistaBasico extends BaseAdapter {

    private Context contexto;
    private ArrayList<Artista> listaArtistas;
    private Festival festival;



    public AdaptadorArtistaBasico(Context contexto, ArrayList<Artista> listaArtistas,Festival festival) {
        this.festival = festival;
        this.contexto = contexto;
        this.listaArtistas = listaArtistas;
    }


    @Override
    public int getCount() {
        return listaArtistas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaArtistas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Artista artista = (Artista) getItem(position);

        convertView = LayoutInflater.from(contexto).inflate(R.layout.item_artista_basico,null);

        //iniciamos los componentes
        ImageView imagenArtista = (ImageView)convertView.findViewById(R.id.imagenArtista);
        TextView nombreArtista = (TextView)convertView.findViewById(R.id.lblNombre);
        TextView fechaConcierto = (TextView)convertView.findViewById(R.id.lblFechaConcierto);

        //imagenFoto
        if(artista.getNombreFoto().equals("default")){
           imagenArtista.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap bitmapImagen = BitmapFactory.decodeByteArray(artista.getFotoArtistaByte(),0,artista.getFotoArtistaByte().length);
            imagenArtista.setImageBitmap(bitmapImagen);
        }
        //nombreArtista
        nombreArtista.setText(artista.getNombreArtista());
        //FALTA POR REVISAR
        //fechaConcierto
        String fechaConciertoString = "Fecha sin determinar";
        //fechaConcierto.setText();
        for(int i=0;i<artista.getListaConciertos().size();i++){
            Concierto conciertoArtista = artista.getListaConciertos().get(i);
            if(conciertoArtista.getFestival().getIdFestival() == festival.getIdFestival()){
                fechaConciertoString = UtilFechas.procesarFechaConcierto(conciertoArtista.getFechaConcierto());
                break;
            }


        }

        fechaConcierto.setText(fechaConciertoString);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent pantallaDetalleArtista = new Intent(contexto, PantallaDetalleArtista.class);
                    pantallaDetalleArtista.putExtra("artista", artista);
                    pantallaDetalleArtista.putExtra("festival",festival);
                    contexto.startActivity(pantallaDetalleArtista);

            }
        });




        return convertView;



    }


    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Artista> getListaArtistas() {
        return listaArtistas;
    }

    public void setListaArtistas(ArrayList<Artista> listaArtistas) {
        this.listaArtistas = listaArtistas;
    }



}
