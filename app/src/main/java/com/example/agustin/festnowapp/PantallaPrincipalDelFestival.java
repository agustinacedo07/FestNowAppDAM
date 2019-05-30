package com.example.agustin.festnowapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.example.adrian.pruebapantallamenu.fragmentos.Fragment02;
import com.example.adrian.pruebapantallamenu.fragmentos.Fragment03;
 */
import com.example.agustin.festnowapp.fragmentos.Fragment01;
import com.example.agustin.festnowapp.fragmentos.Fragment02;
import com.example.agustin.festnowapp.fragmentos.Fragment03;

public class PantallaPrincipalDelFestival extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private ViewPager view1;
    private LinearLayout paginainformacion;
    private LinearLayout paginaartistas;
    private LinearLayout paginanoticias;


    private TextView camino;
    RatingBar ratingratingBar;
    Button btnValorar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
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

        //implementacion slider
        view1=(ViewPager) findViewById(R.id.view);
        view1.setAdapter(new AdminPageAdapter());

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
                        paginainformacion = (LinearLayout) LayoutInflater.from(PantallaPrincipalDelFestival.this).inflate(R.layout.paginainformacion, null);
                    }
                    paginaactual = paginainformacion;
                    break;
                case 1:
                    if (paginaartistas == null)
                    {
                        paginaartistas = (LinearLayout) LayoutInflater.from(PantallaPrincipalDelFestival.this).inflate(R.layout.paginaartistas, null);
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

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }


        @Override
        public void destroyItem(View collection, int position, Object view)
        {
            ((ViewPager) collection).removeView((View) view);
        }

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

        FragmentManager fragmentManager=getSupportFragmentManager();


        if (id == R.id.nav_camera) {

            fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment01()).commit();

        } else if (id == R.id.nav_gallery) {

            fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment02()).commit();

        } else if (id == R.id.nav_send) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment03()).commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void valora(View view) {
        ratingratingBar = (RatingBar) findViewById(R.id.ratingVal);
        //btnValorar = (Button) findViewById(R.id.btnValorar);
    
        cargarVal();


    }

    private void cargarVal() {
        float val= (int) ratingratingBar.getRating();

        Toast.makeText(getApplicationContext(),"La valoracion es: "+val,Toast.LENGTH_LONG).show();

    }













}
