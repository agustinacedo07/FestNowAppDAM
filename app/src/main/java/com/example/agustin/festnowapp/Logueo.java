package com.example.agustin.festnowapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import modelos.Comando;

/**
 * Clase que establece la conexion con el servidor y da opción para el logueo del usuario
 */
public class Logueo extends AppCompatActivity implements View.OnClickListener {
    //para comunicación con el SERVIDOR
    private static Socket skCliente;

    //flujos de comunicacion
    //FLUJOS DE COMUNICACION
    //flujo de datos
    public static DataInputStream flujoDatosEntrada;
    public static DataOutputStream flujoDatosSalida;
    //flujo de objetos
    public static ObjectInputStream flujoEntradaObjetos;
    public static ObjectOutputStream flujoSalidaObjetos;



    //elementos de la pantalla
    private EditText usuario, contraseña;
    private Button entrar;
    private TextView enlaceRegistro;

    //elementos para el logueo
    String usuarioLogin,passLogin;

    //metodo constructo de la ventana
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);


        entrar = (Button) findViewById(R.id.entrar);
        usuario = (EditText) findViewById(R.id.usuario);
        enlaceRegistro = (TextView) findViewById(R.id.enlaceRegistro);
        contraseña = (EditText) findViewById(R.id.contraseña);

        enlaceRegistro.setOnClickListener(this);


        if(skCliente==null){
            new ConexionServer().execute();

        }


        //boton de aceptar login
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 usuarioLogin = usuario.getText().toString();
                 passLogin =  contraseña.getText().toString();

                 new LoginServer().execute();
            }
        });


    }

    private boolean abrirFlujos() {
        try {

            skCliente = new Socket("192.168.1.128",2000);


            InputStream entrada = skCliente.getInputStream();
            OutputStream salida = skCliente.getOutputStream();


            flujoSalidaObjetos = new ObjectOutputStream(salida);
            flujoEntradaObjetos = new ObjectInputStream(skCliente.getInputStream());
            flujoDatosSalida = new DataOutputStream(salida);
            flujoDatosEntrada = new DataInputStream(entrada);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }


    @Override
    public void onClick(View v) {

        Intent REGIS = new Intent(getApplicationContext(), Registro.class);
        startActivity(REGIS);
    }




    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class ConexionServer extends AsyncTask<Void ,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Void... voids) {
            return abrirFlujos();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Toast.makeText(getApplicationContext(),"Conexion realizada con éxito",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Conexion NO REALIZADA",Toast.LENGTH_LONG).show();
            }
        }
    }


    private class LoginServer extends  AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean validacionLogin = false;
            Comando comando = new Comando();
            comando.setOrden("login");
            comando.getArgumentos().add(usuarioLogin);
            comando.getArgumentos().add(passLogin);

            try {
                flujoSalidaObjetos.writeObject(comando);
                validacionLogin = flujoDatosEntrada.readBoolean();
            } catch (IOException e) {
                validacionLogin=false;
            }

            return validacionLogin;
        }


        @Override
        protected void onPostExecute(Boolean validacionLogin) {
            if(validacionLogin){
                Intent pantallaPrincipal = new Intent(getApplicationContext(),PantallaPrincipal.class);
                startActivity(pantallaPrincipal);
                Toast.makeText(getApplicationContext(),"Bienvenido a la Aplicación FEST NOW",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Usuario y contraseña incorrectos",Toast.LENGTH_LONG).show();
            }

            usuario.setText("");
            contraseña.setText("");
        }
    }




}


