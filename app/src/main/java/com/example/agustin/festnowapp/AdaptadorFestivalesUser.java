package com.example.agustin.festnowapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.agustin.festnowapp.Util.SesionServer;
import com.example.agustin.festnowapp.Util.UtilFechas;

import java.util.ArrayList;

import modelos.Artista;
import modelos.Festival;

public class AdaptadorFestivalesUser extends BaseAdapter{
    private Context contexto;
    private static ArrayList<Festival> listaFestivales;
    private ListView listaFestivalesUsuario;

    public AdaptadorFestivalesUser(Context contexto, ArrayList<Festival> listaFestivales,ListView listaFestivalesUsuario) {
        this.contexto = contexto;
        this.listaFestivales = listaFestivales;
        this.listaFestivalesUsuario = listaFestivalesUsuario;
    }

    @Override
    public int getCount() {
        return listaFestivales.size();
    }

    public static void eliminarFestival(int posicion){
        listaFestivales.remove(posicion);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Festival festival = (Festival) getItem(position);


        convertView = LayoutInflater.from(contexto).inflate(R.layout.huecolistafesti,null);
        ImageView imagenFoto = (ImageView)convertView.findViewById(R.id.imagenLogoListadoArtista);
        TextView titulo = (TextView)convertView.findViewById(R.id.titulo);
        TextView fecha = (TextView)convertView.findViewById(R.id.fecha);
        TextView ciudad = (TextView)convertView.findViewById(R.id.ciudad);
        TextView artistas = (TextView)convertView.findViewById(R.id.artistas);
        TextView precioMedio = (TextView)convertView.findViewById(R.id.precioMedio);
        RatingBar ratingValoraciones = (RatingBar)convertView.findViewById(R.id.ratingValoracion);
        Button btnSeguir = (Button)convertView.findViewById(R.id.btnSeguirFestival);
        TextView txtNumValoraciones = (TextView)convertView.findViewById(R.id.txtNumValoraciones);

        if(festival.getNombreFotoLogo().equals("default")){
            imagenFoto.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(festival.getImagenLogo(),0,festival.getImagenLogo().length);
            imagenFoto.setImageBitmap(bitmap);
        }

        titulo.setText(festival.getNombre());
        fecha.setText(UtilFechas.procesarFechaFestival(festival.getFechaInicio(),festival.getFechaFin()));
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
        float valoracionFestival = (float)festival.getValoracion();
        int numeroValoraciones = festival.getNumValoraciones();
        ratingValoraciones.setRating(valoracionFestival);
        String numValoracionesCadena = Integer.toString(numeroValoraciones);
        txtNumValoraciones.setText("/"+numValoracionesCadena);



        final AdaptadorFestivalesUser adaptador = this;

        //funcionalidad del boton seguir Festival
        btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new SeguirFestivalServer(contexto,festival,position,adaptador).execute(Integer.toString(SesionServer.clienteAplicacion.getIdCliente()),Integer.toString(festival.getIdFestival()));
            }
        });






        return convertView;
    }


    public static ArrayList<Festival> getListaFestivales() {
        return listaFestivales;
    }

    public static void setListaFestivales(ArrayList<Festival> listaFestivales) {
        AdaptadorFestivalesUser.listaFestivales = listaFestivales;
    }

    public ListView getListaFestivalesUsuario() {
        return listaFestivalesUsuario;
    }

    public void setListaFestivalesUsuario(ListView listaFestivalesUsuario) {
        this.listaFestivalesUsuario = listaFestivalesUsuario;
    }
}
