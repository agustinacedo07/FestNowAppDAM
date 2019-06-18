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
import android.widget.TextView;

import java.util.ArrayList;

import modelos.Cliente;
import modelos.Festival;

public class AdaptadorSeguidoresBasico extends BaseAdapter {
    private Context contexto;
    private ArrayList<Cliente> listaSeguidores;
    private ListView listaSeguidoresPantalla;
    private FragmentSeguidores fragmentSeguidores;

    public AdaptadorSeguidoresBasico(Context contexto,ArrayList<Cliente>listaSeguidores,ListView listaSeguidoresPantalla,FragmentSeguidores fragmentSeguidores){
        this.contexto = contexto;
        this.listaSeguidores = listaSeguidores;
        this.listaSeguidoresPantalla = listaSeguidoresPantalla;
        this.fragmentSeguidores = fragmentSeguidores;
    }


    @Override
    public int getCount() {
        return listaSeguidores.size();
    }

    @Override
    public Object getItem(int position) {
        return listaSeguidores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(contexto).inflate(R.layout.itemseguidores,null);

        Cliente cliente = (Cliente) getItem(position);

        ImageView imagenUsuario = (ImageView)convertView.findViewById(R.id.imagenPerfilSegBasico);
        TextView nombreUsuario = (TextView)convertView.findViewById(R.id.usuarioSegBasico);
        TextView nombre = (TextView)convertView.findViewById(R.id.nombreSegBasico);
        TextView ciudadUsuario = (TextView)convertView.findViewById(R.id.ciudadSegBasico);


        if(cliente.getNombreFoto().equals("default")){
            imagenUsuario.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap imagenByte = BitmapFactory.decodeByteArray(cliente.getFotoByte(),0,cliente.getFotoByte().length);
            imagenUsuario.setImageBitmap(imagenByte);
        }

        nombreUsuario.setText(cliente.getUsuario());

        nombre.setText(cliente.getNombre()+" "+cliente.getApellidos());

        ciudadUsuario.setText(cliente.getLocalidad()+"/"+ cliente.getProvincia());



        return convertView;
    }
}
