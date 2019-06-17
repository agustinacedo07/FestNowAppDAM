package com.example.agustin.festnowapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.example.agustin.festnowapp.Util.SesionUserServer;

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


    //video
    VideoView videoView;


    //elementos de la pantalla
    private EditText usuario, contraseña;
    private Button entrar;
    private TextView enlaceRegistro;

    //elementos para el logueo
    String usuarioLogin,passLogin;




    //metodo constructor de la ventana
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);

        //FALTA IMPLEMENTAR
        videoView = (VideoView) findViewById(R.id.videofondo);
        String videoPath = "android.resource://com.example.agustin.festnowapp/"+R.raw.recortadofinal;
        Uri uri= Uri.parse(videoPath);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();


        entrar = (Button) findViewById(R.id.entrar);
        usuario = (EditText) findViewById(R.id.usuario);
        enlaceRegistro = (TextView) findViewById(R.id.enlaceRegistro);
        contraseña = (EditText) findViewById(R.id.contraseña);

        //damos funcionalidad al enlace que nos redireccionará a la página de registro
        enlaceRegistro.setOnClickListener(this);

        //por si se vuelve a la pantalla de logueo tras un registro que no inicialize de nuevo la conexion
        if(SesionUserServer.skClienteUser==null){
            new SesionServer().execute();

        }


        //boton de aceptar login
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioLogin = usuario.getText().toString();
                passLogin =  contraseña.getText().toString();
                //control de campos vacíos
                if(usuarioLogin.isEmpty() || passLogin.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Los campos son obligatorios",Toast.LENGTH_LONG).show();
                }else if(SesionUserServer.skClienteUser == null){//control de conexion
                    Toast.makeText(getApplicationContext(),"No tiene conexion a la red",Toast.LENGTH_LONG).show();
                }else{
                    new LoginServer().execute();

                }


            }
        });


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
     * Clase que realiza el Logueo del cliente
     */
    private class LoginServer extends  AsyncTask<Void,Void,Boolean>{
        Comando comando = new Comando();

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean validacionLogin = false;
            comando.setOrden("login");
            comando.getArgumentos().add(usuarioLogin);
            comando.getArgumentos().add(passLogin);

            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                comando = (Comando) SesionUserServer.flujoEntradaObjetosUser.readObject();
                 validacionLogin = comando.isRespuestaBooleana();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Problema con la conexion con el Servidor",Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return validacionLogin;
        }



        @Override
        protected void onPostExecute(Boolean validacionLogin) {
            if(validacionLogin){
                //creamos una variable estática con todos los datos del cliente logueado
                SesionUserServer.clienteAplicacion = (Cliente) comando.getArgumentos().get(0);
                Intent pantallaPrincipal = new Intent(getApplicationContext(),PantallaPrincipal.class);
                startActivity(pantallaPrincipal);
                Toast.makeText(getApplicationContext(),"Bienvenido a la Aplicación FEST NOW",Toast.LENGTH_LONG).show();
                //evita que vuelva a la pantalla de logueo si ya ha iniciado sesion
                //finish();
            }else{
                Toast.makeText(getApplicationContext(),"Usuario y contraseña incorrectos",Toast.LENGTH_LONG).show();
            }

            usuario.setText("");
            contraseña.setText("");
        }
    }


    /**
     * Clase que ejecuta un hilo que realiza la conexion con el servidor e inicializa su conexion y flujos para usarlo
     * en la ejecución del prorgama
     */
    private class SesionServer extends AsyncTask<Void,Void,Boolean> {
        //atributos de comunicación con el servidor
        Socket skCliente;
        //flujo de datos
        DataInputStream flujoDatosEntrada;
        DataOutputStream flujoDatosSalida;
        //flujo de objetos
        ObjectInputStream flujoEntradaObjetos;
        ObjectOutputStream flujoSalidaObjetos;



        @Override
        protected Boolean doInBackground(Void... voids) {
            return abrirFlujos();
        }


        /**
         * Abre los flujos de comunicación con el servidor
         * @return - un booleano indicando si la conexion se ha realizado o no
         */
        private boolean abrirFlujos() {
            try {

                skCliente = new Socket(Constantes.IP_CONEXION,1990);


                InputStream entrada = skCliente.getInputStream();
                OutputStream salida = skCliente.getOutputStream();


                flujoSalidaObjetos = new ObjectOutputStream(salida);
                flujoEntradaObjetos = new ObjectInputStream(skCliente.getInputStream());
                flujoDatosSalida = new DataOutputStream(salida);
                flujoDatosEntrada = new DataInputStream(entrada);


                SesionUserServer.skClienteUser = skCliente;
                SesionUserServer.flujoSalidaObjetosUser = flujoSalidaObjetos;
                SesionUserServer.flujoEntradaObjetosUser = flujoEntradaObjetos;
                SesionUserServer.flujoDatosSalidaUser = flujoDatosSalida;
                SesionUserServer.flujoDatosEntradaUser = flujoDatosEntrada;



                return true;



            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }

        }
    }









}


