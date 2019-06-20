package com.example.agustin.festnowapp.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.UtilFechas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase que representa al Fragment que muestra la información del festival al detalle
 */
public class FragmentInformacion extends Fragment {

    //crea una instancia del fragment y prepara sus argumentos
    public static FragmentInformacion newInstance(Festival festival) {
        Bundle args = new Bundle();
        args.putSerializable("festival",festival);
        FragmentInformacion fragment = new FragmentInformacion();
        fragment.setArguments(args);
        return fragment;
    }

    //crea la vista del fragment y lo devuelve para mostrarlo
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Festival festival = (Festival) getArguments().getSerializable("festival");
        View rootView = inflater.inflate(R.layout.pagina_informacion,container,false);

        //componentes de la vista
        ImageView imagenFestivalPrincipal = (ImageView)rootView.findViewById(R.id.imagenPrincipal);
        ImageView imagenLogoFestival = (ImageView)rootView.findViewById(R.id.imagenLogo);
        TextView txtLugar = (TextView)rootView.findViewById(R.id.labelLugar);
        TextView txtFecha = (TextView)rootView.findViewById(R.id.labelFecha);
        Button btnWeb = (Button) rootView.findViewById(R.id.btnWeb);
        ImageButton btnInstagram = (ImageButton)rootView.findViewById(R.id.btnInstagram);
        ImageButton btnTwitter = (ImageButton)rootView.findViewById(R.id.btnTwitter);
        ImageButton btnFacebook = (ImageButton)rootView.findViewById(R.id.btnFacebook);
        TextView txtDescripcion = (TextView)rootView.findViewById(R.id.labelDescripcion);
        SupportMapFragment mapa = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.fragmentMapa);
        RatingBar valoracionesEstrellas = (RatingBar)rootView.findViewById(R.id.ratingValoracionDetalle);
        TextView txtNumValoraciones = (TextView)rootView.findViewById(R.id.labelNumValoraciones);
        TextView txtNumSeguidores = (TextView)rootView.findViewById(R.id.labelNumSeguidores);



        //****************** CREACIÓN E INICIALIZACIÓN DE LOS COMPONENTES DE LA VISTA ***************
        //imagen del festival
        if(!festival.getNombreFotoPral().equals("default")){
            Bitmap imagenByte = BitmapFactory.decodeByteArray(festival.getImagenPral(),0,festival.getImagenPral().length);
            imagenFestivalPrincipal.setImageBitmap(imagenByte);
        }else{
            imagenFestivalPrincipal.setImageResource(R.mipmap.logo2);
        }

        //imagen logo del festival
        if(!festival.getNombreFotoLogo().equals("default")){
            Bitmap imagenByte = BitmapFactory.decodeByteArray(festival.getImagenLogo(),0,festival.getImagenLogo().length);
            imagenLogoFestival.setImageBitmap(imagenByte);
        }else{
            imagenLogoFestival.setImageResource(R.mipmap.logo2);
        }

        //lugar
        txtLugar.setText(festival.getLocalidad());

        //fecha
        String fechaProcesada = UtilFechas.procesarFechaFestival(festival.getFechaInicio(),festival.getFechaFin());
        txtFecha.setText(fechaProcesada);

        //btnWeb
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(festival.getSitioWeb());
                Intent webFestival = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(webFestival);
            }
        });

        /*
        Los metodos de redes sociales primeron intentan abrir el perfil del usuario a través la aplicación y si no
        tiene la aplicación instalada salta una excepción que abre el perfil desde el navegador
         */
        //btnInstagram
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scheme = "http://instagram.com/_u/" + festival.getInstagram();
                String path = "https://instagram.com/" + festival.getInstagram();
                Intent paginaInstagram = null;
                try {
                    paginaInstagram = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
                } catch (Exception e) {
                    paginaInstagram = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                } finally {
                    startActivity(paginaInstagram);

                }
            }
        });

        //btnTwitter
        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paginaTwitter = null;


                paginaTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + festival.getNombrePerfilTwitter()));

                startActivity(paginaTwitter);
            }
        });


        //btnFacebook
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debemos recoger otro campo con el nombre de la pagina web en facebook
                String paginaFacebook = "https://www.facebook.com/" + festival.getNombrePerfilFacebook() + "/";
                String facebookID = "fb://page/" + festival.getFacebook();

                //controla si tienes instalada la aplicación de facebook o la lanza por el navegador
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookID)));
                } catch (Exception e) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(paginaFacebook)));
                }

            }
        });

        //descripcion del festival
        txtDescripcion.setText(festival.getDescripcion());

        //valoracion estrellas
        float valoracionFestival = (float)festival.getValoracion();
        int numSeguidores = festival.getNumSeguidores();

        valoracionesEstrellas.setRating(valoracionFestival);
        txtNumValoraciones.setText("/"+Integer.toString(festival.getNumValoraciones()));
        txtNumSeguidores.setText(Integer.toString(numSeguidores)+" seguidores");

        //mapa
        mapa.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.clear();

                googleMap.addMarker(new MarkerOptions().position(new LatLng(festival.getLatitud(), festival.getLongitud())).title(festival.getNombre()).snippet(festival.getDescripcion()));


                CameraPosition camaraMapa = CameraPosition.builder().target(new LatLng(festival.getLatitud(), festival.getLongitud()
                ))
                        .zoom(10).bearing(0).tilt(45).build();

                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camaraMapa));

            }
        });


        return rootView;

    }
}
