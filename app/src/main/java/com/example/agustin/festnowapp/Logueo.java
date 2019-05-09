package com.example.agustin.festnowapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Logueo extends AppCompatActivity implements View.OnClickListener {

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


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreUsu = usuario.getText().toString();
                String nombrePass = contraseña.getText().toString();

                new LoginWS().execute("http://192.168.1.37/WebServiceFestNow/scripts/login.php?user="+nombreUsu+"&pass="+nombrePass);

            }
        });


    }


    @Override
    public void onClick(View v) {

        Intent REGIS = new Intent(getApplicationContext(), Registro.class);
        startActivity(REGIS);
    }




    private class  LoginWS extends  AsyncTask<String,Void,String>{

        //descarga y procesa la URL de respuesta del WebService
        @Override
        protected String doInBackground(String... strings) {
            try{
                return WebServiceUtil.dowloadUrl(strings[0]);
            }catch (IOException ex){
                return "URL no válida";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            if(s.contains("1")){
                Intent pantallaPrincipal = new Intent(getApplicationContext(),PantallaPrincipal.class);
                startActivity(pantallaPrincipal);
            }else{
                Toast.makeText(getApplicationContext(),"INCORRECTO",Toast.LENGTH_LONG).show();

            }
        }
    }



}


