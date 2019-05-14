package com.example.agustin.festnowapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

import modelos.Cliente;
import modelos.Comando;


public class Registro extends AppCompatActivity {

    private EditText etiUsuario,etiPass,etiNombre,etiApellidos,etiFechaNacimiento,etiLocalidad,etiProvincia,etiComunidad,etiPais,etiCorreo,etiTelefono;
    private Button btnAceptarLogin;
    private Cliente nuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etiUsuario=(EditText)findViewById(R.id.etiUsuario);
        etiPass=(EditText)findViewById(R.id.etiPass);
        etiNombre=(EditText)findViewById(R.id.etiNombre);
        etiApellidos=(EditText)findViewById(R.id.etiApellidos);
        etiFechaNacimiento=(EditText)findViewById(R.id.etiFechaNacimiento);
        etiLocalidad=(EditText)findViewById(R.id.etiLocalidad);
        etiProvincia=(EditText)findViewById(R.id.etiProvincia);
        etiComunidad=(EditText)findViewById(R.id.etiComunidad);
        etiPais=(EditText)findViewById(R.id.etiPais);
        etiCorreo=(EditText)findViewById(R.id.etiCorreo);
        etiTelefono=(EditText)findViewById(R.id.etiTelefono);
        btnAceptarLogin=(Button)findViewById(R.id.btnEnviarRegistro);

        btnAceptarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cojemos las variables introducidas por el usuario
                String usuario = etiUsuario.getText().toString();
                String pass = etiPass.getText().toString();
                String nombre = etiNombre.getText().toString();
                String apellidos = etiApellidos.getText().toString();
                String fecha = etiFechaNacimiento.getText().toString();
                String localidad = etiLocalidad.getText().toString();
                String provincia = etiProvincia.getText().toString();
                String comunidad = etiComunidad.getText().toString();
                String pais = etiPais.getText().toString();
                String mail = etiCorreo.getText().toString();
                String telefono = etiTelefono.getText().toString();

                nuevoUsuario = new Cliente();
                nuevoUsuario.setNombre(nombre);
                nuevoUsuario.setApellidos(apellidos);
                nuevoUsuario.setUsuario(usuario);
                nuevoUsuario.setPass(pass);
                nuevoUsuario.setFechaNacimiento(fecha);
                nuevoUsuario.setLocalidad(localidad);
                nuevoUsuario.setProvincia(provincia);
                nuevoUsuario.setComunidad(comunidad);
                nuevoUsuario.setPais(pais);
                nuevoUsuario.setMail(mail);
                nuevoUsuario.setTelefono(telefono);
                nuevoUsuario.setFotoPerfil(null);
                nuevoUsuario.setTipoUsuario("user");


                new RegistroServer().execute();








            }
        });
    }


    private class RegistroServer extends AsyncTask<Void,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Void... voids) {


            boolean respuesta = false;
            Comando comando = new Comando();

            comando.setOrden("reg");
            comando.getArgumentos().add(nuevoUsuario);

            try {
                Logueo.flujoSalidaObjetos.writeObject(comando);
                respuesta = Logueo.flujoDatosEntrada.readBoolean();

            } catch (IOException e) {
                e.printStackTrace();
                respuesta = false;
            }finally {
                return  respuesta;
            }


        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean == true){
                Toast.makeText(getApplicationContext(),"Se ha registrado el usuario ("+nuevoUsuario.getUsuario()+" correctamente",Toast.LENGTH_LONG).show();
                Intent pantallaLogueo = new Intent(getApplicationContext(),Logueo.class);
                startActivity(pantallaLogueo);
            }else {

                Toast.makeText(getApplicationContext(),"Incorrecto",Toast.LENGTH_LONG).show();

            }
        }
    }



}
