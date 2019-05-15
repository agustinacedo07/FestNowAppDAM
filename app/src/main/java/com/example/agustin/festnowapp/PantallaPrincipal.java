package com.example.agustin.festnowapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PantallaPrincipal extends AppCompatActivity {

    private ArrayList<Festival> listaFestis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        listaFestis =new ArrayList<Festival>();
        listaFestis.add(new Festival("Festi1", 'm'));
        listaFestis.add(new Festival("Festi2",'m'));
        listaFestis.add(new Festival("Festi1",'m'));
        listaFestis.add(new Festival("Festi5",'f'));
        listaFestis.add(new Festival("Festi6",'f'));
        listaFestis.add(new Festival("Festi7",'f'));
        listaFestis.add(new Festival("Festi8",'m'));


        AdaptadorPersonas adaptador = new AdaptadorPersonas(this);
        ListView lv1 = (ListView)findViewById(R.id.list1);
        lv1.setAdapter(adaptador);


    }

    class AdaptadorPersonas extends ArrayAdapter<Festival> {

        AppCompatActivity appCompatActivity;

        AdaptadorPersonas(AppCompatActivity context) {
            super(context, R.layout.festival, listaFestis);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.festival, null);

            TextView textView1 = (TextView)item.findViewById(R.id.textView3);
            textView1.setText(listaFestis.get(position).getNombre());

            ImageView imageView1 = (ImageView)item.findViewById(R.id.imageView);
            if (listaFestis.get(position).getGenero()=='m')
                imageView1.setImageResource(R.mipmap.logo2);
            else
                imageView1.setImageResource(R.mipmap.logo3);
            return(item);
        }
    }

}
