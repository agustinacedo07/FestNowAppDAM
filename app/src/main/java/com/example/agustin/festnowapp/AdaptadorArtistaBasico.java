package com.example.agustin.festnowapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import modelos.Festival;

public class AdaptadorArtistaBasico extends BaseAdapter {
    private Context contexto;
    private ArrayList<Artista> listaArtistas;
    private ListView listaPantallaArtistas;
    private Festival festival;
    private FragmentArtistas pantallaArtistas;



    public AdaptadorArtistaBasico(Context contexto, ArrayList<Artista> listaArtistas, ListView listaPantallaArtistas,Festival festival,FragmentArtistas pantallaArtistas) {
        this.festival = festival;
        this.contexto = contexto;
        this.listaArtistas = listaArtistas;
        this.listaPantallaArtistas = listaPantallaArtistas;
        this.pantallaArtistas = pantallaArtistas;
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

        if(artista.getNombreFoto().equals("default")){
           imagenArtista.setImageResource(R.mipmap.fotovacia);
        }else{
            Bitmap bitmapImagen = BitmapFactory.decodeByteArray(artista.getFotoArtistaByte(),0,artista.getFotoArtistaByte().length);
            imagenArtista.setImageBitmap(bitmapImagen);
        }

        nombreArtista.setText(artista.getNombreArtista());
        String fechaConciertoString = "Fecha sin determinar";
        //fechaConcierto.setText();
        for(int i=0;i<artista.getListaConciertos().size();i++){
            Concierto conciertoArtista = artista.getListaConciertos().get(0);
            if(conciertoArtista.getFestival().equals(festival)){
                fechaConciertoString = UtilFechas.procesarFechaConcierto(conciertoArtista.getFechaConcierto());
                break;
            }


        }


        fechaConcierto.setText(fechaConciertoString);




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantallaArtistas.detalleArtista(artista);
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

    public ListView getListaPantallaArtistas() {
        return listaPantallaArtistas;
    }

    public void setListaPantallaArtistas(ListView listaPantallaArtistas) {
        this.listaPantallaArtistas = listaPantallaArtistas;
    }


}
