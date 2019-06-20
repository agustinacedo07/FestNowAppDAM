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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.agustin.festnowapp.Activitys.PantallaDetalleFestival;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.UtilFechas;

import java.util.ArrayList;

import modelos.Artista;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Adaptador para mostrar los items de los festivales que sigue el usuario en la pantalla de FestivalesSeguidos
 */
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
        convertView = LayoutInflater.from(contexto).inflate(R.layout.item_festivales_follow,null);

        //adaptar componentes de item
        ImageView imagenFoto = (ImageView)convertView.findViewById(R.id.imagenLogoFestFollow);
        TextView titulo = (TextView)convertView.findViewById(R.id.tituloFestFollow);
        TextView fecha = (TextView)convertView.findViewById(R.id.fechaFestFollow);
        TextView ciudad = (TextView)convertView.findViewById(R.id.ciudadFestFollow);
        TextView artistas = (TextView)convertView.findViewById(R.id.artistasFestFollow);
        RatingBar valoracion = (RatingBar)convertView.findViewById(R.id.ratingValoracionFestFollow);
        TextView numValoraciones = (TextView)convertView.findViewById(R.id.txtValoracionFestFollow);



        //imagenFoto
        if(festival.getNombreFotoLogo().equals("default")){
            imagenFoto.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(festival.getImagenLogo(),0,festival.getImagenLogo().length);
            imagenFoto.setImageBitmap(bitmap);
        }
        //titulo
        titulo.setText(festival.getNombre());
        //fecha
        fecha.setText(UtilFechas.procesarFechaFestival(festival.getFechaInicio(),festival.getFechaFin()));
        //ciudad
        ciudad.setText(festival.getLocalidad());
        //artistas cabezas de cartel
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
        float valoracionFestival = (float)festival.getValoracion();
        String numeroValoracionesFest = Integer.toString(festival.getNumValoraciones());
        valoracion.setRating(valoracionFestival);
        numValoraciones.setText(numeroValoracionesFest);

        //implementación del click en el festival, para que muestre su pantalla de detalle
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaDetalleFestival = new Intent(contexto,PantallaDetalleFestival.class);
                pantallaDetalleFestival.putExtra("festival",festival);
                contexto.startActivity(pantallaDetalleFestival);            }
        });


        return  convertView;
    }




    //*************** GETTER AND SETTERS ***********************
    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Festival> getListaFestivalesSeguidos() {
        return listaFestivalesSeguidos;
    }

    public void setListaFestivalesSeguidos(ArrayList<Festival> listaFestivalesSeguidos) {
        this.listaFestivalesSeguidos = listaFestivalesSeguidos;
    }

    public ListView getListaPantallaFestivales() {
        return listaPantallaFestivales;
    }

    public void setListaPantallaFestivales(ListView listaPantallaFestivales) {
        this.listaPantallaFestivales = listaPantallaFestivales;
    }
}
