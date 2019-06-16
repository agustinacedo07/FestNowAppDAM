package com.example.agustin.festnowapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agustin.festnowapp.Util.UtilFechas;

import modelos.Artista;
import modelos.Concierto;
import modelos.Festival;

public class PantallaDetalleArtista extends AppCompatActivity {
    private ImageView imagenArtista;
    private ImageView imagenLogoFest;
    private TextView fechaConcierto;
    private ImageButton btnSpotify;
    private TextView nombreArtista;
    private TextView descripcionArtista;
    private ListView listaFestivalesArtista;

    private Artista artista;
    private Festival festival;




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setContentView(R.layout.detalle_artista_festival);

            artista = (Artista) getIntent().getExtras().get("artista");
            festival = (Festival) getIntent().getExtras().get("festival");

            //instanciar los elementos de la pantalla
            imagenArtista = (ImageView) findViewById(R.id.imagenDetalleArtista);
            imagenLogoFest = (ImageView)findViewById(R.id.imagenLogoDetalleArtista);
            fechaConcierto = (TextView)findViewById(R.id.etiFechaConcierto);
            btnSpotify = (ImageButton)findViewById(R.id.btnSpotify);
            nombreArtista = (TextView)findViewById(R.id.etiNombreArtistaDetalle);
            descripcionArtista = (TextView)findViewById(R.id.etiDescripcionArtistaDetalle);
            listaFestivalesArtista = (ListView)findViewById(R.id.listaFestivalesArtistaDetalle);

            //imagen artista
            if(!artista.getNombreFoto().equals("default")){
                Bitmap imagenArtistaByte = BitmapFactory.decodeByteArray(artista.getFotoArtistaByte(),0,artista.getFotoArtistaByte().length);
                imagenArtista.setImageBitmap(imagenArtistaByte);
            }else{
                imagenArtista.setImageResource(R.mipmap.logo2);
            }

            //imagen logo del festival
            if(!festival.getNombreFotoLogo().equals("default")){
                Bitmap imagenLogoFestByte = BitmapFactory.decodeByteArray(festival.getImagenLogo(),0,festival.getImagenLogo().length);
                imagenLogoFest.setImageBitmap(imagenLogoFestByte);
            }else{
                imagenLogoFest.setImageResource(R.mipmap.logo2);
            }

            //fecha de concierto
            String fechaConciertoProcesada = "Fecha sin determinar";
            for(int i=0;i<artista.getListaConciertos().size();i++){
                Concierto conciertoArtista = artista.getListaConciertos().get(i);
                if(conciertoArtista.getFestival().equals(festival)){
                    fechaConciertoProcesada = UtilFechas.procesarFechaConcierto(conciertoArtista.getFechaConcierto());
                    break;
                }
            }

            fechaConcierto.setText(fechaConciertoProcesada);


            //funcionalidad del boton de spotify
            btnSpotify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String paginaSpotify = "https://open.spotify.com/artist/"+artista.getEnlaceSpotify();
                    String spotifyID = "spotify:artist:"+artista.getEnlaceSpotify();

                    try{
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(spotifyID)));
                    }catch (Exception e){
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(paginaSpotify)));
                    }

                }
            });

            //nombre artista
            nombreArtista.setText(artista.getNombreArtista());

            //descripcion artista
            descripcionArtista.setText(artista.getDescripcion());



            //lista de festivales
            AdaptadorFestivalesArtista adaptadorListaFestivales = new AdaptadorFestivalesArtista(getApplicationContext(),artista.getListaFestivales(),listaFestivalesArtista,this,artista);
            listaFestivalesArtista.setAdapter(adaptadorListaFestivales);



    }



    public void lanzarDetalleFestival (Festival festival){
        Intent pantallaDetalleFestival = new Intent(getApplicationContext(),PantallaPrincipalDelFestival.class);
        pantallaDetalleFestival.putExtra("festival",festival);
        startActivity(pantallaDetalleFestival);
    }
}
