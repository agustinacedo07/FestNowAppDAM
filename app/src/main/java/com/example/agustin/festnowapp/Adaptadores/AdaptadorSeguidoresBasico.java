package com.example.agustin.festnowapp.Adaptadores;

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

import com.example.agustin.festnowapp.R;

import java.util.ArrayList;

import modelos.Cliente;

/**
 * @author Agustin/Adrian
 * Adaptador para los items de Clientes en la lista de Seguidores del detalle de los festivales
 */
public class AdaptadorSeguidoresBasico extends BaseAdapter {
    private Context contexto;
    private ArrayList<Cliente> listaSeguidores;
    private ListView listaSeguidoresPantalla;

    public AdaptadorSeguidoresBasico(Context contexto,ArrayList<Cliente>listaSeguidores,ListView listaSeguidoresPantalla){
        this.contexto = contexto;
        this.listaSeguidores = listaSeguidores;
        this.listaSeguidoresPantalla = listaSeguidoresPantalla;
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
        Cliente cliente = (Cliente) getItem(position);

        convertView = LayoutInflater.from(contexto).inflate(R.layout.item_seguidor,null);


        ImageView imagenUsuario = (ImageView)convertView.findViewById(R.id.imagenPerfilSegBasico);
        TextView nombreUsuario = (TextView)convertView.findViewById(R.id.usuarioSegBasico);
        TextView nombre = (TextView)convertView.findViewById(R.id.nombreSegBasico);
        TextView ciudadUsuario = (TextView)convertView.findViewById(R.id.ciudadSegBasico);

        //imagenUsuario
        if(cliente.getNombreFoto().equals("default")){
            imagenUsuario.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap imagenByte = BitmapFactory.decodeByteArray(cliente.getFotoByte(),0,cliente.getFotoByte().length);
            imagenUsuario.setImageBitmap(imagenByte);
        }
        //nombreUsuario
        nombreUsuario.setText(cliente.getUsuario());
        //nombreUsuario
        nombre.setText(cliente.getNombre()+" "+cliente.getApellidos());
        //ciudad del usuario
        ciudadUsuario.setText(cliente.getLocalidad()+"/"+ cliente.getProvincia());

        return convertView;
    }
}
