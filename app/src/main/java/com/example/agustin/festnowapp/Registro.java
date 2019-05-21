package com.example.agustin.festnowapp;


import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import modelos.Cliente;
import modelos.Comando;


public class Registro extends AppCompatActivity implements View.OnClickListener {
     static final int REQUEST_IMAGE_CAPTURE =  1;
    //elementos de la pantalla
    private EditText etiUsuario,etiPass,etiNombre,etiApellidos,etiFechaNacimiento,etiLocalidad,etiProvincia,etiComunidad,etiPais,etiCorreo,etiTelefono;
    private Button btnAceptarRegistro;
    private Cliente nuevoUsuario;

    ImageView imagen;
    private Button botonFoto;


    private String mPath = "";


    Button bfecha;
    EditText fechaNacimiento;
    private int dia, mes, ano;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        imagen=(ImageView) findViewById(R.id.imagenId);


        /* variables elegir foto y camara */

        bfecha=(Button)findViewById(R.id.bfecha);
        fechaNacimiento=(EditText) findViewById(R.id.fechaNacimiento);
        botonFoto=(Button) findViewById(R.id.btnSelFoto);


        bfecha.setOnClickListener(this);




        etiUsuario=(EditText)findViewById(R.id.etiUsuario);
        etiPass=(EditText)findViewById(R.id.etiPass);
        etiNombre=(EditText)findViewById(R.id.etiNombre);
        etiApellidos=(EditText)findViewById(R.id.etiApellidos);
        //instanciar fecha a la actual
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = new Date();
        etiFechaNacimiento=(EditText)findViewById(R.id.fechaNacimiento);
        etiFechaNacimiento.setText(formatoFecha.format(fechaActual));
        etiLocalidad=(EditText)findViewById(R.id.etiLocalidad);
        etiProvincia=(EditText)findViewById(R.id.etiProvincia);
        etiComunidad=(EditText)findViewById(R.id.etiComunidad);
        etiPais=(EditText)findViewById(R.id.etiPais);
        etiCorreo=(EditText)findViewById(R.id.etiCorreo);
        etiTelefono=(EditText)findViewById(R.id.etiTelefono);
        btnAceptarRegistro=(Button)findViewById(R.id.btnEnviarRegistro);


        /**
         * Funcionalidad del boton Aceptar Registro
         */
        btnAceptarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                //PASAR LOS DATOS PRIMERO POR CONTROL DE ERRORES
                //Implementar por Adrían
                String nombre = etiNombre.getText().toString();
                String apellidos = etiApellidos.getText().toString();
                String localidad = etiLocalidad.getText().toString();
                String provincia = etiProvincia.getText().toString();
                String comunidad = etiComunidad.getText().toString();
                String pais = etiPais.getText().toString();
                String mail = etiCorreo.getText().toString();
                String usuario = etiUsuario.getText().toString();
                String pass = etiPass.getText().toString();
                String telefono = etiTelefono.getText().toString();
                String tipoUsuario = "user";
                String fechaString = fechaNacimiento.getText().toString();
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNacimientoUser = null;
                try {
                    fechaNacimientoUser = formatoFecha.parse(fechaString);
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(),"Problema al formar la fecha",Toast.LENGTH_LONG).show();
                }


                /*
                Cosas controladas por el .xml:
                    Tamaño de los campos, nombre no mayor de 15 letras, etc.
                    Teclado para el telefono.
                 */
                if(nombre.equals("")||(apellidos.equals("")||(localidad.equals("")||(provincia.equals("")||(comunidad.equals("")||(pais.equals("")||(mail.equals("")||(usuario.equals("")||(pass.equals("")||(telefono.equals("")||fechaString.equals(""))))))))))){
                    Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios, no pueden estar vacios",Toast.LENGTH_LONG).show();
                }else if (ControlErrores.controlTelefono(telefono)==false) {
                    Toast.makeText(getApplicationContext(),"El campo telefono esta mal introducido, debe de ser numerico y empezar por 6,7 o 9",Toast.LENGTH_LONG).show();
                }else if(ControlErrores.controlCaracter(nombre)==false) {
                    Toast.makeText(getApplicationContext(), "El campo nombre no puede contener numeros", Toast.LENGTH_LONG).show();
                }else if(ControlErrores.controlCaracter(apellidos)==false) {
                    Toast.makeText(getApplicationContext(), "El campo apellidos no puede contener numeros", Toast.LENGTH_LONG).show();
                }else{




                    //si pasa todos los controles de errores
                    Cliente nuevoCliente = new Cliente();


                    //imagen de perfil que guardaremos en un array de bytes
                    byte[] fotoPerfilByte = null;
                    //si se ha seleccionado foto se crea un fichero y se convierte en un array de bytes
                    if (!mPath.equals("")) {
                        File imagenFotoPerfil = new File(mPath);
                        fotoPerfilByte = new byte[(int) imagenFotoPerfil.length()];
                        nuevoCliente.setNombreFoto("perfil" + usuario);//se le pone un nombre para la fotografía
                    } else {
                        //si no se ha elegido foto el nombre será default que se guardará en la base de datos y se creará el array con un solo elemento
                        //para evitar que sea nulo
                        nuevoCliente.setNombreFoto("default");
                        fotoPerfilByte = new byte[1];
                    }


                    nuevoCliente.setNombre(nombre);
                    nuevoCliente.setApellidos(apellidos);
                    nuevoCliente.setLocalidad(localidad);
                    nuevoCliente.setProvincia(provincia);
                    nuevoCliente.setComunidad(comunidad);
                    nuevoCliente.setPais(pais);
                    nuevoCliente.setMail(mail);
                    nuevoCliente.setUsuario(usuario);
                    nuevoCliente.setPass(pass);
                    nuevoCliente.setFechaNacimiento(fechaNacimientoUser);
                    nuevoCliente.setFotoByte(fotoPerfilByte);
                    nuevoCliente.setTelefono(telefono);
                    nuevoCliente.setTipoUsuario(tipoUsuario);

                    nuevoUsuario = nuevoCliente;

                    new RegistroServer().execute();


                }



            }
        });
    }




    /**
     * Hilo que recoge los datos de el registro de el nuevo ususario y lo registra en la BD
     */
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


    @Override
    public void onClick(View v) {

        final Calendar c = Calendar.getInstance();

        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                fechaNacimiento.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }
                , dia, mes, ano);
        datePickerDialog.show();
    }




    public void cimag(View view) {
        cargarImagen();
    }
    private void cargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            imagen.setImageURI(path);
        }

        //tomar foto
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode ==RESULT_OK){
            Bundle extras=data.getExtras();
            Bitmap imageBitmap=(Bitmap)extras.get("data");
            imagen.setImageBitmap(imageBitmap);
        }


    }

    public void selfoto(View view) {
        llamarIntent();

    }

    private void llamarIntent() {
        //declaramos el intent definido de la camara
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }




}





