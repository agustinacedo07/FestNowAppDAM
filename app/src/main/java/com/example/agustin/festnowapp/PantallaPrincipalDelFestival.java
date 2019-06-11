package com.example.agustin.festnowapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.example.adrian.pruebapantallamenu.fragmentos.Fragment02;
import com.example.adrian.pruebapantallamenu.fragmentos.Fragment03;
 */
import com.example.agustin.festnowapp.fragmentos.Fragment01;
import com.example.agustin.festnowapp.fragmentos.Fragment02;
import com.example.agustin.festnowapp.fragmentos.Fragment03;
import com.example.agustin.festnowapp.fragmentos.Fragment04;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modelos.Artista;
import modelos.Festival;

public class PantallaPrincipalDelFestival extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private ViewPager view1;
    private ScrollView paginainformacion;
    private LinearLayout paginaartistas;
    private LinearLayout paginanoticias;


    private TextView camino;
    RatingBar ratingratingBar;
    Button btnValorar;

    private Festival festival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

           festival = (Festival) getIntent().getExtras().get("festival");
           setContentView(R.layout.activity_main);
           Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);

           FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
           fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
               }
           });

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                   this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
           drawer.addDrawerListener(toggle);
           toggle.syncState();

           NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
           navigationView.setNavigationItemSelectedListener(this);


           try{
               //implementacion slider
               view1=(ViewPager) findViewById(R.id.view);
               view1.setAdapter(new AdminPageAdapter());
           }catch(Exception e){
               e.printStackTrace();
           }






    }

    //implementacion slider
    class AdminPageAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position)
        {
            View paginaactual = null;
            switch (position)
            {
                case 0:
                    if (paginainformacion == null)
                    {
                        try{

                            paginainformacion = (ScrollView) LayoutInflater.from(PantallaPrincipalDelFestival.this).inflate(R.layout.paginainformacion, null);

                            //scrooll de pagina
                            paginainformacion.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    findViewById (R.id.listaCabezas) .getParent (). requestDisallowInterceptTouchEvent (false);
                                    return false;
                                }
                            });





                            ImageView imagenPrincipal = (ImageView)paginainformacion.findViewById(R.id.imagenPrincipal);
                            ImageView imagenLogo = (ImageView)paginainformacion.findViewById(R.id.imagenLogo);
                            TextView ciudad = (TextView)paginainformacion.findViewById(R.id.labelLugar);
                            TextView fecha = (TextView)paginainformacion.findViewById(R.id.labelFecha);
                            TextView descripcion = (TextView)paginainformacion.findViewById(R.id.labelDescripcion);
                            ImageButton btnFacebook = (ImageButton)paginainformacion.findViewById(R.id.btnFacebook);
                            Button btnWeb = (Button)paginainformacion.findViewById(R.id.btnWeb);
                            ImageButton btnInstagram = (ImageButton)paginainformacion.findViewById(R.id.btnInstagram);
                            ImageButton btnTwitter = (ImageButton)paginainformacion.findViewById(R.id.btnTwitter);
                            ListView listaCabezasCartel = (ListView)paginainformacion.findViewById(R.id.listaCabezas);

                            //scroll de vista
                            listaCabezasCartel.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    v.getParent ().requestDisallowInterceptTouchEvent (true);
                                    return false;
                                }
                            });

                            /*
                            //para controlar los scroll de la pantalla y de la lista de artistas
                            listaCabezasCartel.setOnTouchListener(new ListView.OnTouchListener(){

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    int action = event.getAction();
                                    switch (action){
                                        case MotionEvent.ACTION_DOWN:
                                            v.getParent().requestDisallowInterceptTouchEvent(true);
                                            break;
                                        case MotionEvent.ACTION_UP:
                                            v.getParent().requestDisallowInterceptTouchEvent(false);
                                            break;
                                    }

                                    v.onTouchEvent(event);

                                    return true;
                                }
                            });
                            */


                            Date fechaFestivalInicio = festival.getFechaInicio();
                            Date fechaFestivalFin = festival.getFechaFin();
                            String fechaPantalla = "";
                            //procesamos la fecha
                            SimpleDateFormat formatoAnyo = new SimpleDateFormat("yyyy");
                            SimpleDateFormat formatoMes = new SimpleDateFormat("MM");
                            SimpleDateFormat formatoDía = new SimpleDateFormat("dd");
                            String anyo = formatoAnyo.format(fechaFestivalInicio);
                            String mes = formatoMes.format(fechaFestivalInicio);
                            mes = procesarMes(mes);
                            String diaInicio = formatoDía.format(fechaFestivalInicio);
                            String diaFin = formatoDía.format(fechaFestivalFin);

                            fechaPantalla = diaInicio+" - "+diaFin+" de "+mes+" del "+anyo;



                            fecha.setText(fechaPantalla);
                            ciudad.setText(festival.getLocalidad());
                            descripcion.setText(festival.getDescripcion());
                            imagenLogo.setImageBitmap(BitmapFactory.decodeByteArray(festival.getImagenLogo(),0,festival.getImagenLogo().length));
                            imagenPrincipal.setImageBitmap(BitmapFactory.decodeByteArray(festival.getImagenPral(),0,festival.getImagenPral().length));
                            btnWeb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri uri = Uri.parse(festival.getSitioWeb());
                                    Intent webFestival = new Intent(Intent.ACTION_VIEW,uri);
                                    startActivity(webFestival);
                                }
                            });

                            btnFacebook.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //debemos recoger otro campo con el nombre de la pagina web en facebook
                                    String paginaFacebook = "https://www.facebook.com/"+festival.getNombrePerfilFacebook()+"/";
                                    String facebookID = "fb://page/"+festival.getFacebook();

                                    //controla si tienes instalada la aplicación de facebook o la lanza por el navegador
                                    try{
                                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(facebookID)));
                                    }catch (Exception e){

                                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(paginaFacebook)));
                                    }

                                }
                            });

                            btnInstagram.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String scheme = "http://instagram.com/_u/"+festival.getInstagram();
                                    String path = "https://instagram.com/"+festival.getInstagram();
                                    Intent paginaInstagram = null;
                                    try {
                                        paginaInstagram = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
                                    } catch (Exception e) {
                                        paginaInstagram = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                                    }finally {
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




                            ArrayList<Artista> artistasCabezasCartel = obtenerCabezasCartel(festival.getListaArtistas());

                            AdaptadorArtistaBasico adaptadorCabezasCartel = new AdaptadorArtistaBasico(getApplicationContext(),artistasCabezasCartel,listaCabezasCartel,this);
                            listaCabezasCartel.setAdapter(adaptadorCabezasCartel);

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                    paginaactual = paginainformacion;
                    break;
                case 1:
                    if (paginaartistas == null)
                    {
                        paginaartistas = (LinearLayout) LayoutInflater.from(PantallaPrincipalDelFestival.this).inflate(R.layout.paginaartistas, null);

                        ListView listaArtistasView = (ListView)paginaartistas.findViewById(R.id.listaArtistasGeneral);

                        AdaptadorArtistaBasico adaptadorArtistasBasico = new AdaptadorArtistaBasico(getApplicationContext(),festival.getListaArtistas(),listaArtistasView,this);
                        listaArtistasView.setAdapter(adaptadorArtistasBasico);

                    }
                    paginaactual = paginaartistas;
                    break;
                case 2:
                    if (paginanoticias == null)
                    {
                        paginanoticias = (LinearLayout) LayoutInflater.from(PantallaPrincipalDelFestival.this).inflate(R.layout.paginanoticias, null);
                    }
                    paginaactual = paginanoticias;
                    break;
            }
            ViewPager vp=(ViewPager) collection;
            vp.addView(paginaactual, 0);
            return paginaactual;
        }

        private String procesarMes(String mes) {
            String mesCadena = "";
            switch (mes){
                case "01":
                    mesCadena = "Enero";
                break;
                case "02":
                    mesCadena = "Febrero";
                    break;
                case "03":
                    mesCadena = "Marzo";
                    break;
                case "04":
                    mesCadena = "Abril";
                    break;
                case "05":
                    mesCadena = "Mayo";
                    break;
                case "06":
                    mesCadena = "Junio";
                    break;
                case "07":
                    mesCadena = "Julio";
                    break;
                case "08":
                    mesCadena = "Agosto";
                    break;
                case "09":
                    mesCadena = "Septiembre";
                    break;
                case "10":
                    mesCadena = "Octubre";
                    break;
                case "11":
                    mesCadena = "Noviembre";
                    break;
                case "12":
                    mesCadena = "Diciembre";
                    break;











            }

            return mesCadena;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }


        public void detalleArtista(Artista artista){
            Toast.makeText(getApplicationContext(),"Ha pulsado al artista "+artista.getNombreArtista(),Toast.LENGTH_LONG).show();
        }


        @Override
        public void destroyItem(View collection, int position, Object view)
        {
            ((ViewPager) collection).removeView((View) view);
        }

    }

    private ArrayList<Artista> obtenerCabezasCartel(ArrayList<Artista> listaArtistas) {
        ArrayList <Artista> listaCabezasCartel = new ArrayList<>();
        for (Artista artista : listaArtistas){
            if(artista.getCabezaCartel()==1){
                listaCabezasCartel.add(artista);
            }
        }

        return listaCabezasCartel;
    }

    public void irPaginaInformacion(View v) {
        view1.setCurrentItem(0);
    }

    public void irPaginaArtistas(View v) {
        view1.setCurrentItem(1);
    }

    public void irPaginaNoticias(View v) {
        view1.setCurrentItem(2);
    }







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








}
