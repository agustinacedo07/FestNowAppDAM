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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.agustin.festnowapp.Util.UtilFechas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import modelos.Artista;
import modelos.Festival;

public class AdaptadorFestivalesFollow extends BaseAdapter {
    private Context contexto;
    private ArrayList<Festival>listaFestivalesSeguidos;
    private ListView listaPantallaFestivales;
    private FestivalesSeguidos pantallaFestivalesSeguidos;


    public AdaptadorFestivalesFollow(Context contexto, ArrayList<Festival> listaFestivalesSeguidos, ListView listaPantallaFestivales,FestivalesSeguidos pantallaFestivalesSeguidos) {
        this.contexto = contexto;
        this.listaFestivalesSeguidos = listaFestivalesSeguidos;
        this.listaPantallaFestivales = listaPantallaFestivales;
        this.pantallaFestivalesSeguidos = pantallaFestivalesSeguidos;
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
        TextView fecha = (TextView)convertView.findViewById(R.id.fecha);
        TextView ciudad = (TextView)convertView.findViewById(R.id.ciudad);
        TextView artistas = (TextView)convertView.findViewById(R.id.artistas);
        RatingBar valoracion = (RatingBar)convertView.findViewById(R.id.ratingValoracionFollow);
        TextView numValoraciones = (TextView)convertView.findViewById(R.id.txtNumValoracionesFollow);



        //modificar los elementos por los valores del festival
        //foto
        if(festival.getNombreFotoPral().equals("default")){
            imagenFoto.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(festival.getImagenLogo(),0,festival.getImagenLogo().length);
            imagenFoto.setImageBitmap(bitmap);
        }

        titulo.setText(festival.getNombre());
        fecha.setText(UtilFechas.procesarFechaFestival(festival.getFechaInicio(),festival.getFechaFin()));
        ciudad.setText(festival.getLocalidad());
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




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nos llevaria a la otra pantalla a la que le pasaremos el festival completo
                //Toast.makeText(contexto,"Ha pulsado el Festival "+festival.getNombre(),Toast.LENGTH_LONG).show();
                pantallaFestivalesSeguidos.lanzarDetalleFestival(festival);

            }
        });


        return  convertView;
    }


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
