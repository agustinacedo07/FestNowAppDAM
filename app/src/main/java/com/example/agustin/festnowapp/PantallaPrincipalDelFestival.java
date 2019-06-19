package com.example.agustin.festnowapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.agustin.festnowapp.Util.UtilFechas;
import com.example.agustin.festnowapp.fragmentos.Fragment02;
import com.example.agustin.festnowapp.fragmentos.Fragment04;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import modelos.Artista;
import modelos.Festival;

public class PantallaPrincipalDelFestival extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


    private ViewPager view1;
    static Festival festival;
    private AdminFragmentAdapter adaptadorFragment;
    private Button btnAtras;
    static Context contexto;

    public static int numPantallas = 6;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            festival = (Festival) getIntent().getExtras().get("festival");
            setContentView(R.layout.content_main);
            setTitle(festival.getNombre());

            //ejecutamos operaciones de la BD
            new FragmentArtistas.ObtenerArtistasFestival(festival).execute();
            new FragmentNoticias.ObtenerNoticias(festival).execute();
            new FragmentSeguidores.ObtenerSeguidores(festival).execute();
            new FragmentComentarios.ObtenerComentarios(festival).execute();



            btnAtras = (Button)findViewById(R.id.btnAtras);
            btnAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pantallaFestivalesSeguidos = new Intent(getApplicationContext(),FestivalesSeguidos.class);
                    startActivity(pantallaFestivalesSeguidos);

                }
            });

            view1=(ViewPager) findViewById(R.id.view);
            adaptadorFragment = new AdminFragmentAdapter(getSupportFragmentManager());
            view1.setAdapter(adaptadorFragment);





    }



    class AdminFragmentAdapter extends FragmentPagerAdapter {


        public AdminFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int posicion) {
            try{
                switch (posicion){
                    case 0://informacion del festival
                        return FragmentInformacion.newInstance(festival);
                    case 1://artistas del festival
                        return FragmentArtistas.newInstance(festival,getApplicationContext());
                    case 2://noticias del festival
                        return FragmentNoticias.newInstance(festival,getApplicationContext());
                    case 3://seguidores
                        return  FragmentSeguidores.newInstance(festival,getApplicationContext());
                    case 4://comentarios
                        return FragmentComentarios.newInstance(festival,getApplicationContext());
                    case 5:
                        return FragmentValoraciones.newInstance(getApplicationContext());
                }
                return  null;
            }catch(Exception e){
                return null;
            }

        }

        //cantidad de pantalla que habrá
        @Override
        public int getCount() {
            return PantallaPrincipalDelFestival.numPantallas;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0://informacion del festival
                    return "Información";
                case 1://artistas del festival
                    return "Artistas";
                case 2://noticias del festival
                    return "Noticias";
                case 3://seguidores
                    return "Lista de Seguidores";
                case 4://comentarios
                    return "Comentarios";
                case 5://valoracion del festival
                    return "Valora el Festival";


            }

            return null;
        }

        //MODIFICACION WAWA
        /*
        @Override
        public int getCount() {
            return 3;
        }
        */
        //MODIFICACION WAWA
        /*
        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View paginaactual = null;
            switch (position) {
                case 0:
                    if (paginainformacion == null) {
                        try {

                            paginainformacion = (ScrollView) LayoutInflater.from(PantallaPrincipalDelFestival.this).inflate(R.layout.paginainformacion, null);


                            ImageView imagenPrincipal = (ImageView) paginainformacion.findViewById(R.id.imagenPrincipal);
                            ImageView imagenLogo = (ImageView) paginainformacion.findViewById(R.id.imagenLogo);
                            TextView ciudad = (TextView) paginainformacion.findViewById(R.id.labelLugar);
                            TextView fecha = (TextView) paginainformacion.findViewById(R.id.labelFecha);
                            TextView descripcion = (TextView) paginainformacion.findViewById(R.id.labelDescripcion);
                            ImageButton btnFacebook = (ImageButton) paginainformacion.findViewById(R.id.btnFacebook);
                            Button btnWeb = (Button) paginainformacion.findViewById(R.id.btnWeb);
                            ImageButton btnInstagram = (ImageButton) paginainformacion.findViewById(R.id.btnInstagram);
                            ImageButton btnTwitter = (ImageButton) paginainformacion.findViewById(R.id.btnTwitter);
                            final SupportMapFragment mapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMapa);

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

                            TextView labelNumeroSeguidores = (TextView) paginainformacion.findViewById(R.id.labelNumSeguidores);
                            TextView labelNumValoraciones = (TextView) paginainformacion.findViewById(R.id.labelNumValoraciones);
                            RatingBar ratingValoracionFestDetalle = (RatingBar) paginainformacion.findViewById(R.id.ratingValoracionDetalle);
                            float valoracionFest = (float) festival.getValoracion();
                            String numValoraciones = Integer.toString(festival.getNumValoraciones());
                            int numeroSeguidores = festival.getNumSeguidores();


                            ratingValoracionFestDetalle.setRating(valoracionFest);
                            labelNumValoraciones.setText(numValoraciones);
                            labelNumeroSeguidores.setText(Integer.toString(numeroSeguidores) + " Seguidores");


                            String fechaProcesada = UtilFechas.procesarFechaFestival(festival.getFechaInicio(), festival.getFechaFin());


                            fecha.setText(fechaProcesada);
                            ciudad.setText(festival.getLocalidad());
                            descripcion.setText(festival.getDescripcion());
                            imagenLogo.setImageBitmap(BitmapFactory.decodeByteArray(festival.getImagenLogo(), 0, festival.getImagenLogo().length));
                            imagenPrincipal.setImageBitmap(BitmapFactory.decodeByteArray(festival.getImagenPral(), 0, festival.getImagenPral().length));
                            btnWeb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri uri = Uri.parse(festival.getSitioWeb());
                                    Intent webFestival = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(webFestival);
                                }
                            });

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


                            btnTwitter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    Intent paginaTwitter = null;

                                    //paginaTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + festival.getNombrePerfilTwitter()));
                                    paginaTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + festival.getNombrePerfilTwitter()));

                                    paginaTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + festival.getNombrePerfilTwitter()));

                                    startActivity(paginaTwitter);


                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    paginaactual = paginainformacion;
                    break;
                case 1:
                    if (paginaartistas == null) {
                        paginaartistas = (LinearLayout) LayoutInflater.from(PantallaPrincipalDelFestival.this).inflate(R.layout.paginaartistas, null);


                        ListView listaArtistasView = (ListView) paginaartistas.findViewById(R.id.listaArtistasGeneral);


                        AdaptadorArtistaBasico adaptadorArtistasBasico = new AdaptadorArtistaBasico(getApplicationContext(), festival.getListaArtistas(), listaArtistasView, this, festival);
                        listaArtistasView.setAdapter(adaptadorArtistasBasico);

                    }
                    paginaactual = paginaartistas;
                    break;
                case 2:
                    if (paginanoticias == null) {
                        paginanoticias = (LinearLayout) LayoutInflater.from(PantallaPrincipalDelFestival.this).inflate(R.layout.paginanoticias, null);
                    }
                    paginaactual = paginanoticias;
                    break;
            }
            ViewPager vp = (ViewPager) collection;
            vp.addView(paginaactual, 0);
            return paginaactual;
        }*/


        //MODIFICACION WAWA
        /*
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        */




    }


    //MODIFICACION WAWA
    /*
    public void irPaginaInformacion(View v) {
        view1.setCurrentItem(0);
    }

    public void irPaginaArtistas(View v) {
        view1.setCurrentItem(1);
    }

    public void irPaginaNoticias(View v) {
        view1.setCurrentItem(2);
    }*/







    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.




        int id = item.getItemId();


        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        Fragment02 fragmento2=new Fragment02();
        Fragment04 fragmento4=new Fragment04();


        if (id == R.id.nav_camera) {

            transaction.add(R.id.contenedor,  fragmento4);
            transaction.addToBackStack(null);
            transaction.commit();



        } else if (id == R.id.nav_gallery) {

            transaction.add(R.id.contenedor,  fragmento2);
            transaction.addToBackStack(null);
            transaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public void detalleArtista(Artista artista) {
        try {
            Intent pantallaDetalleArtista = new Intent(getApplicationContext(), PantallaDetalleArtista.class);
            pantallaDetalleArtista.putExtra("artista", artista.getNombreArtista());
            //pantallaDetalleArtista.putExtra("festival", festival);
            startActivity(pantallaDetalleArtista);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
