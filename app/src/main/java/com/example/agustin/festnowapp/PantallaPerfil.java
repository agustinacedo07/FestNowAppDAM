package com.example.agustin.festnowapp;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agustin.festnowapp.Util.SesionUserServer;
import com.example.agustin.festnowapp.Util.UtilFechas;

public class PantallaPerfil extends AppCompatActivity {
    private Button btnCambiarApellido,btnCambiarNombre,btnCambiarUsuario,cambiarPass;
    private TextView nombreUsuario,telefono,correo,localidad,fechaNacimiento,apellidos,nombre;
    private ImageView imagenUsuario;

    String campoMoficar = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_perfil);

        btnCambiarApellido = (Button)findViewById(R.id.btncambiarapellidos);
        btnCambiarNombre = (Button)findViewById(R.id.btncambiarNombre);
        btnCambiarUsuario = (Button)findViewById(R.id.btnCambiarNombreUsuario);
        nombreUsuario = (TextView) findViewById(R.id.etiNombreUsuario);
        telefono = (TextView)findViewById(R.id.etiTelefono);
        correo = (TextView)findViewById(R.id.etiCorreo);
        localidad = (TextView)findViewById(R.id.etiLocalidad);
        fechaNacimiento = (TextView)findViewById(R.id.etiFechaNacimiento);
        apellidos = (TextView)findViewById(R.id.etiapellidos);
        nombre = (TextView)findViewById(R.id.etiNombre);
        imagenUsuario = (ImageView)findViewById(R.id.imagenPerfil);
        cambiarPass = (Button)findViewById(R.id.btnCambiarContraseña);


        btnCambiarApellido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoMoficar = "apellidos";
                crearDialogo(PantallaPerfil.this);
            }
        });

        btnCambiarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoMoficar = "nombre";
            }
        });

        btnCambiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoMoficar = "user";
            }
        });

        cambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoMoficar = "pass";
            }
        });

        nombreUsuario.setText(SesionUserServer.clienteAplicacion.getNombre());
        telefono.setText(SesionUserServer.clienteAplicacion.getTelefono());
        correo.setText(SesionUserServer.clienteAplicacion.getMail());
        localidad.setText(SesionUserServer.clienteAplicacion.getLocalidad()+"/"+SesionUserServer.clienteAplicacion.getProvincia());
        fechaNacimiento.setText(UtilFechas.procesarFechaConcierto(SesionUserServer.clienteAplicacion.getFechaNacimiento()));
        apellidos.setText(SesionUserServer.clienteAplicacion.getApellidos());
        nombre.setText(SesionUserServer.clienteAplicacion.getNombre());


        if(SesionUserServer.clienteAplicacion.getNombreFoto().equals("default")){
            imagenUsuario.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap imagenUserByte = BitmapFactory.decodeByteArray(SesionUserServer.clienteAplicacion.getFotoByte(),0,SesionUserServer.clienteAplicacion.getFotoByte().length);
            imagenUsuario.setImageBitmap(imagenUserByte);
        }



    }

    private void crearDialogo(PantallaPerfil pantallaPerfil) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(pantallaPerfil);
        constructor.setTitle("Titulo");
        AlertDialog dialogo = constructor.create();
        dialogo.show();

    }


    private class UpdateUsuario extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
