package com.example.agustin.festnowapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.ArrayList;

public class Logueo extends AppCompatActivity implements View.OnClickListener {
    //para comunicación con el SERVIDOR
    private Socket skCliente;
    public  static Comando comando;

    //flujos de comunicacion
    //FLUJOS DE COMUNICACION
    //flujo de datos
    public static DataInputStream flujoDatosEntrada;
    public static DataOutputStream flujoDatosSalida;
    //flujo de objetos
    public static ObjectInputStream flujoEntradaObjetos;
    public static ObjectOutputStream flujoSalidaObjetos;




    private EditText usuario, contraseña;
    private Button entrar;
    private TextView enlaceRegistro;
    private boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);


        entrar = (Button) findViewById(R.id.entrar);
        usuario = (EditText) findViewById(R.id.usuario);
        enlaceRegistro = (TextView) findViewById(R.id.enlaceRegistro);
        contraseña = (EditText) findViewById(R.id.contraseña);

        enlaceRegistro.setOnClickListener(this);


        comando = new Comando();


        new ConexionServer().execute();


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreUsu = usuario.getText().toString();
                String nombrePass = contraseña.getText().toString();

                comando.setOrden("login");
                ArrayList<Object> argumentos = new ArrayList<Object>();
                argumentos.add(nombreUsu);
                argumentos.add(nombrePass);
                comando.setArgumentos(argumentos);

                Toast.makeText(getApplicationContext(),skCliente.toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

    private boolean abrirFlujos() {
        try {

            skCliente = new Socket("192.168.1.37",2000);


            InputStream entrada = skCliente.getInputStream();
            OutputStream salida = skCliente.getOutputStream();

            flujoDatosEntrada = new DataInputStream(entrada);
            flujoDatosSalida = new DataOutputStream(salida);
            flujoSalidaObjetos = new ObjectOutputStream(salida);
            flujoEntradaObjetos = new ObjectInputStream(entrada);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View v) {

        Intent REGIS = new Intent(getApplicationContext(), Registro.class);
        startActivity(REGIS);
    }




    private class  ConexionServer extends  AsyncTask<Void,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Void... voids) {
            return abrirFlujos();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean == true){
                Toast.makeText(getApplicationContext(),"Conexion realizada con éxito",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"No se ha podido establecer la conexion",Toast.LENGTH_LONG).show();

            }
        }
    }



}


