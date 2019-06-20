package com.example.agustin.festnowapp.Activitys;

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
import android.widget.Toast;

import com.example.agustin.festnowapp.Activitys.Logueo;
import com.example.agustin.festnowapp.Dialogos.DialogoUpdatePass;
import com.example.agustin.festnowapp.Dialogos.DialogoUpdateUser;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;
import com.example.agustin.festnowapp.Util.UtilFechas;

import java.io.IOException;

import modelos.Comando;

/**
 * @author Agustin/Adrian
 * Pantalla de perfil del usuario logueado
 */
public class PantallaPerfil extends AppCompatActivity {
    private Button btnCerrarSesion,btnCambiarUsuario,cambiarPass;
    private TextView telefono,correo,localidad,fechaNacimiento,apellidos,nombre,nombreUsuario;
    private ImageView imagenUsuario;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_perfil);

        btnCerrarSesion = (Button)findViewById(R.id.btnCerrarSesion);
        btnCambiarUsuario = (Button)findViewById(R.id.btnCambiarUsuario);
        cambiarPass = (Button)findViewById(R.id.btnCambiarPass);
        telefono = (TextView)findViewById(R.id.etiTelefono);
        correo = (TextView)findViewById(R.id.etiCorreo);
        localidad = (TextView)findViewById(R.id.etiLocalidad);
        fechaNacimiento = (TextView)findViewById(R.id.etiFechaNacimiento);
        apellidos = (TextView)findViewById(R.id.etiapellidos);
        nombre = (TextView)findViewById(R.id.etiNombre);
        imagenUsuario = (ImageView)findViewById(R.id.imagenPerfil);
        nombreUsuario = (TextView)findViewById(R.id.etiNombreUsuario);


        //****************** BOTONES PARA CAMBIO DE DATOS Y CIERRE DE SESION **********************
        btnCambiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogoUpdateUser(getApplicationContext()).show(getSupportFragmentManager(),"");
            }
        });

        cambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogoUpdatePass(getApplicationContext()).show(getSupportFragmentManager(),"");
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CerrarSesionUser().execute();
            }
        });



        nombre.setText(SesionUserServer.clienteAplicacion.getNombre());
        telefono.setText(SesionUserServer.clienteAplicacion.getTelefono());
        correo.setText(SesionUserServer.clienteAplicacion.getMail());
        localidad.setText(SesionUserServer.clienteAplicacion.getLocalidad()+"/"+SesionUserServer.clienteAplicacion.getProvincia());
        fechaNacimiento.setText(UtilFechas.procesarFechaConcierto(SesionUserServer.clienteAplicacion.getFechaNacimiento()));
        apellidos.setText(SesionUserServer.clienteAplicacion.getApellidos());
        nombreUsuario.setText(SesionUserServer.clienteAplicacion.getUsuario());


        if(SesionUserServer.clienteAplicacion.getNombreFoto().equals("default")){
            imagenUsuario.setImageResource(R.mipmap.logo2);
        }else{
            Bitmap imagenUserByte = BitmapFactory.decodeByteArray(SesionUserServer.clienteAplicacion.getFotoByte(),0,SesionUserServer.clienteAplicacion.getFotoByte().length);
            imagenUsuario.setImageBitmap(imagenUserByte);
        }



    }


    /**
     * Hilo que se ejecuta en segundo plano para realizar el cierre de la sesión con el servidor y el cierre de flujos
     */
    private class CerrarSesionUser extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Comando comando = new Comando();
            comando.setOrden("exit");
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Problema al cerrar la sesión",Toast.LENGTH_LONG).show();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent pantallaLogin = new Intent(getApplicationContext(), Logueo.class);
            startActivity(pantallaLogin);
            //para eliminar todos los datos de la sesión y liberar recursos
            finishAffinity();
        }
    }





}
