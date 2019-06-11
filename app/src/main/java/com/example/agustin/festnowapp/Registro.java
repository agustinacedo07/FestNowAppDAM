package com.example.agustin.festnowapp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.agustin.festnowapp.Util.SesionServer;
import com.example.agustin.festnowapp.Util.Validator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import modelos.Cliente;
import modelos.Comando;


public class Registro extends AppCompatActivity implements View.OnClickListener {

    //elementos de la pantalla
    private EditText etiUsuario,etiPass,etiNombre,etiApellidos,etiFechaNacimiento,etiLocalidad,etiProvincia,etiComunidad,etiPais,etiCorreo,etiTelefono;
    private Button btnAceptarRegistro;
    private Cliente nuevoUsuario;

    //imagen para guardar la imagen del usuario
    private ImageView imagen;
    //boton para realizar la foto
    private Button botonFoto;
    //boton para seleccionar una fecha
    private Button bfecha;
    //elementos para recoger la fecha seleccionada
    private EditText fechaNacimiento;
    private int dia, mes, ano;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        imagen=(ImageView) findViewById(R.id.imagenId);
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





                //preparamos los datos que queremos validar
                String datos [] = new String[11];
                datos[0] = nombre;
                datos[1] = apellidos;
                datos[2] = fechaString;
                datos[3] = localidad;
                datos[4] = provincia;
                datos[5] = comunidad;
                datos[6] = pais;
                datos[7] = mail;
                datos[8] = telefono;
                datos[9] = usuario;
                datos[10] = pass;

                //Longitudes de campo controlados en xml de ... (para evitar conflictos con la base de datos)
                //Nombre,apellidos,localidad,provincia,comunidad,pais,correo,telefono,usuario y pass

                //Control de campos vacíos
                if(nombre.equals("")||(apellidos.equals("")||(localidad.equals("")||(provincia.equals("")||(comunidad.equals("")||(pais.equals("")||(mail.equals("")||(usuario.equals("")||(pass.equals("")||(telefono.equals("")||fechaString.equals(""))))))))))){
                    Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios, no pueden estar vacios",Toast.LENGTH_LONG).show();
                }else{
                    ControlErrores controlErrores = new ControlErrores(datos);
                    if(!controlErrores.isValidacion()){
                        //comprobar los errores y mostrarlos en un cuadro de diálogo
                        mostrarDialogoErrores(controlErrores.getListaValidator());
                    }else{
                        //preparamos el cliente y lo insertamos
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaNacimientoUser = null;
                        try {
                            fechaNacimientoUser = formatoFecha.parse(fechaString);
                        } catch (ParseException e) {
                            Toast.makeText(getApplicationContext(),"Problema al formar la fecha",Toast.LENGTH_LONG).show();
                        }


                        //si pasa todos los controles de errores
                        Cliente nuevoCliente = new Cliente();


                        //imagen de perfil que guardaremos en un array de bytes
                        byte[] fotoPerfilByte = null;
                        //si se ha seleccionado foto se crea un fichero y se convierte en un array de bytes
                        if (imagen!=null) {
                            Bitmap bitMapImagenPerfil = ((BitmapDrawable)imagen.getDrawable()).getBitmap();
                            ByteArrayOutputStream stremSalida = new ByteArrayOutputStream();
                            bitMapImagenPerfil.compress(Bitmap.CompressFormat.JPEG,100,stremSalida);
                            fotoPerfilByte = stremSalida.toByteArray();
                            nuevoCliente.setNombreFoto("perfil" + usuario);//se le pone un nombre para la fotografía
                            nuevoCliente.setFotoByte(fotoPerfilByte);
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

            }
        });
    }







    /**
     * Método para realizar la selección de la fecha a través del botón e instanciar el campo de texto de la fecha
     * @param v
     */
    @Override
    public void onClick(View v) {

        final Calendar c = Calendar.getInstance();

        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                fechaNacimiento.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }
                , dia, mes, ano);
        datePickerDialog.show();
    }




    //MÉTODOS PARA SELECCIONAR Y CARGAR LA IMAGEN DE PERFIL DE EL USUARIO

    /**
     * Método que valora la selección de la imagen e instancia la imagen de la pantalla con la imagen seleccionada
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            imagen.setImageURI(path);
        }

        //tomar foto
        if (requestCode == Constantes.REQUEST_IMAGE_CAPTURE && resultCode ==RESULT_OK){
            Bundle extras=data.getExtras();
            Bitmap imageBitmap=(Bitmap)extras.get("data");
            imagen.setImageBitmap(imageBitmap);
        }


    }


    //seleccionar una foto de la galeria
    public void selfoto(View view) {
        llamarIntent();

    }

    //activa la cámara para realizar una foto
    private void llamarIntent() {
        //declaramos el intent definido de la camara
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent,Constantes.REQUEST_IMAGE_CAPTURE);
        }
    }


    public void cimag(View view) {
        cargarImagen();
    }
    private void cargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);

    }


    /**
     * Método que muestra los errores que tiene la inserción
     * @param listaValidator - lista con el procesamiento de validacion de todos los datos
     */
    public void mostrarDialogoErrores(ArrayList<Validator> listaValidator){
        String mensajeErrores = "";
        for(int i=0;i<listaValidator.size();i++){
            Validator validator = listaValidator.get(i);
            if (!validator.isValidacionCampo()){
                String campo = validator.getCampo();
                String problemaCampo = validator.getProblemaValidacion();
                mensajeErrores += "-"+campo+":\n"+problemaCampo+"\n\n";
            }
        }

         AlertDialog.Builder cuadroDialogo = new AlertDialog.Builder(this);

        cuadroDialogo.setTitle("Errores en Registro");
        cuadroDialogo.setMessage(mensajeErrores);
        cuadroDialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        cuadroDialogo.show();




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
                SesionServer.flujoSalidaObjetos.writeObject(comando);
                respuesta = SesionServer.flujoDatosEntrada.readBoolean();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Problema con la conexion al Servidor",Toast.LENGTH_LONG).show();
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
                //para eliminar el activity de la pila
                finish();
            }else {

                Toast.makeText(getApplicationContext(),"Problemas con la conexion",Toast.LENGTH_LONG).show();

            }
        }
    }





}





