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

import modelos.Cliente;
import modelos.Comando;

/**
 * Clase que establece la conexion con el servidor y da opción para el logueo del usuario
 * Además también establece una serie de variables estáticas que se usarán genericas durante el tiempo
 * que use el cliente la aplicacion
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
    //para que una vez se logue se cree una variable estática con todos los datos del cliente
    public static Cliente clienteAplicacion;



    //metodo constructor de la ventana
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);


        entrar = (Button) findViewById(R.id.entrar);
        usuario = (EditText) findViewById(R.id.usuario);
        enlaceRegistro = (TextView) findViewById(R.id.enlaceRegistro);
        contraseña = (EditText) findViewById(R.id.contraseña);

        //damos funcionalidad al enlace que nos redireccionará a la página de registro
        enlaceRegistro.setOnClickListener(this);

        //por si se vuelve a la pantalla de logueo tras un registro que no inicialize de nuevo la conexion
        if(skCliente==null){
            new ConexionServer().execute();

        }


        //boton de aceptar login
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioLogin = usuario.getText().toString();
                passLogin =  contraseña.getText().toString();
                if(usuarioLogin.isEmpty() && passLogin.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Los campos son obligatorios",Toast.LENGTH_LONG).show();
                }else{
                    new LoginServer().execute();
                }


            }
        });


    }

    /**
     * Abre los flujos de comunicación con el servidor
     * @return - un booleano indicando si la conexion se ha realizado o no
     */
    private boolean abrirFlujos() {
        try {

            skCliente = new Socket(Constantes.IP_CONEXION,2000);


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


    /**
     * Método para dar funcionaliad a el enlace de la pantalla de login que nos llevará a la pantalla de registro
     * @param v -
     */
    @Override
    public void onClick(View v) {

        Intent REGIS = new Intent(getApplicationContext(), Registro.class);
        startActivity(REGIS);
    }


    /**
     * Hilo que se ejecutará de manera concurrente al hilo principal para ejecutar una conexion con el servidor
     */
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
        Comando comando = new Comando();

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean validacionLogin = false;
            comando.setOrden("login");
            comando.getArgumentos().add(usuarioLogin);
            comando.getArgumentos().add(passLogin);

            try {
                flujoSalidaObjetos.writeObject(comando);
                comando = (Comando) flujoEntradaObjetos.readObject();
                 validacionLogin = comando.isRespuestaBooleana();
            } catch (IOException e) {
                validacionLogin=false;
            } catch (ClassNotFoundException e) {
                validacionLogin = false;
            }

            return validacionLogin;
        }



        @Override
        protected void onPostExecute(Boolean validacionLogin) {
            if(validacionLogin){
                //creamos una variable estática con todos los datos del cliente logueado
                clienteAplicacion = (Cliente) comando.getArgumentos().get(0);
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


