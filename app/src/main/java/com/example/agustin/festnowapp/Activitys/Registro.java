package com.example.agustin.festnowapp.Activitys;


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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.agustin.festnowapp.Util.Constantes;
import com.example.agustin.festnowapp.Util.ControlErroresRegistro;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;
import com.example.agustin.festnowapp.Util.UtilFechas;
import com.example.agustin.festnowapp.Util.UtilGeo;
import com.example.agustin.festnowapp.Util.Validator;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import modelos.Cliente;
import modelos.Comando;

/**
 * @author Agustin/Adrian
 * Clase que realiza el registro de un nuevo usuario
 */
public class Registro extends AppCompatActivity implements View.OnClickListener {

    //elementos de la pantalla
    private EditText etiUsuario,etiPass,etiNombre,etiApellidos,etiFechaNacimiento,etiCorreo,etiTelefono;
    private Button btnAceptarRegistro;
    private TextView txtProvincia,txtComunidad,txtPais;
    private AutocompleteSupportFragment autoCompleteFragment;

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

    //variables para almacenar los datos geográficos
    String ciudad = "",provincia = "",comunidad = "",pais = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registro);


        //instanciar los componentes de laq pantalla
        txtProvincia=(TextView)findViewById(R.id.txtProvincia);
        txtComunidad = (TextView)findViewById(R.id.txtComunidad);
        txtPais = (TextView)findViewById(R.id.txtPais);
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
        Calendar fechaActual = Calendar.getInstance();
        etiFechaNacimiento=(EditText)findViewById(R.id.fechaNacimiento);
        etiFechaNacimiento.setText(UtilFechas.valorarFechaPicker(fechaActual.get(Calendar.DAY_OF_MONTH),fechaActual.get(Calendar.MONTH)+1,fechaActual.get(Calendar.YEAR)));
        ano = fechaActual.get(Calendar.YEAR);
        mes = fechaActual.get(Calendar.MONTH)+1;
        dia = fechaActual.get(Calendar.DAY_OF_MONTH);
        etiCorreo=(EditText)findViewById(R.id.etiCorreo);
        etiTelefono=(EditText)findViewById(R.id.etiTelefono);
        btnAceptarRegistro=(Button)findViewById(R.id.btnEnviarRegistro);


        //preparación del buscador de lugares de google
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), Constantes.API_KEY_MAPS);
        }
        autoCompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autoCompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS));

        /**
         * Procesamiento de la búsqueda de ciudad del usuario
         */
        autoCompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            //hacemos que se autocompleten los datos geográficos de comunidad,provincia y pais,segun la busqueda de google
            @Override
                public void onPlaceSelected(@NonNull Place place) {
                    String tokens [] = place.getAddress().split(",");
                    String ciudadSeleccionada = place.getName();
                    String provinciaSeleccionada = tokens[1];
                    String comunidadSeleccionada = UtilGeo.procesarComunidad(provinciaSeleccionada.replace(" ",""));
                    String paisSeleccionado = tokens[2];

                    //actualizamos los elementos de la pantalla
                    txtProvincia.setText(provinciaSeleccionada);
                    txtComunidad.setText(comunidadSeleccionada);
                    txtPais.setText(paisSeleccionado);

                    //actualizamos las variables globales geográficas
                    ciudad = ciudadSeleccionada;
                    provincia = provinciaSeleccionada;
                    comunidad = comunidadSeleccionada;
                    pais = paisSeleccionado;

            }

                @Override
                public void onError(@NonNull Status status) {
                    Toast.makeText(getApplicationContext(),"Error: "+status,Toast.LENGTH_LONG).show();
                }
            });






        /**
         * Funcionalidad del boton Aceptar Registro
         */
        btnAceptarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //recogida de datos introducidas por el usuario
                String usuario = etiUsuario.getText().toString();
                String pass = etiPass.getText().toString();
                String nombre = etiNombre.getText().toString();
                String apellidos = etiApellidos.getText().toString();
                String fechaString = ano+"-"+mes+"-"+dia;
                //los datos geográficos estan recogidos en variables globales
                String mail = etiCorreo.getText().toString();
                String telefono = etiTelefono.getText().toString();
                String tipoUsuario = "user";



                //preparamos los datos que queremos validar
                String datos [] = new String[11];
                datos[0] = nombre;
                datos[1] = apellidos;
                datos[2] = fechaString;
                datos[3] = mail;
                datos[4] = telefono;
                datos[5] = usuario;
                datos[6] = pass;


                //Longitudes de campo controlados en xml de ... (para evitar conflictos con la base de datos)
                //Control de campos vacíos
                if(nombre.equals("")||(apellidos.equals("")||(ciudad.equals("")||(provincia.equals("")||(comunidad.equals("")||(pais.equals("")||(mail.equals("")||(usuario.equals("")||(pass.equals("")||(telefono.equals("")||fechaString.equals(""))))))))))){
                    Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios, no pueden estar vacios",Toast.LENGTH_LONG).show();
                }else{//realizacion de control del resto de campos
                    ControlErroresRegistro controlErrores = new ControlErroresRegistro(datos);
                    if(!controlErrores.isValidacion()){//comprobar los errores y mostrarlos en un cuadro de diálogo
                        mostrarDialogoErrores(controlErrores.getListaValidator());
                    }else if(SesionUserServer.skClienteUser ==null) {//control de conexion con el servidor
                        Toast.makeText(getApplicationContext(),"No tiene conexion",Toast.LENGTH_LONG).show();
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
                        if (imagen.getDrawable()!=null) {
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
                            //evitamos acceso a la foto null
                            fotoPerfilByte = new byte[1];
                        }

                        //construimos el cliente
                        nuevoCliente.setNombre(nombre);
                        nuevoCliente.setApellidos(apellidos);
                        nuevoCliente.setUsuario(usuario);
                        nuevoCliente.setPass(pass);
                        nuevoCliente.setFechaNacimiento(fechaNacimientoUser);
                        nuevoCliente.setLocalidad(ciudad);
                        nuevoCliente.setProvincia(provincia);
                        nuevoCliente.setComunidad(comunidad);
                        nuevoCliente.setPais(pais);
                        nuevoCliente.setMail(mail);
                        nuevoCliente.setTelefono(telefono);
                        nuevoCliente.setTipoUsuario(tipoUsuario);
                        nuevoCliente.setFotoByte(fotoPerfilByte);


                        nuevoUsuario = nuevoCliente;

                        //registramos el usuario en la BD
                        new RegistroServer().execute();
                    }

                }

            }
        });
    }







    //************** SELECCION DE FECHA **************
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


                fechaNacimiento.setText(UtilFechas.valorarFechaPicker(dayOfMonth,month+1,year));
                dia = dayOfMonth;
                mes = month+1;
                ano = year;
            }
        }
                , dia, mes, ano);
        datePickerDialog.show();
    }





    //************** SELECCION DE FOTOGRAFÍA DE PERFIL **************

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


    public void cimag(View view) {
        cargarImagen();
    }

    //activa la cámara para realizar una foto

    /**
     * Activa la cámara, para realizar foto
     */
    private void llamarIntent() {
        //declaramos el intent definido de la camara
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            try{
                startActivityForResult(takePictureIntent,Constantes.REQUEST_IMAGE_CAPTURE);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Carga la imagen seleccionada de la galería del movil
     */
    private void cargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);

    }


    //************** MUESTRA DE ERRORES EN EL REGISTRO **************

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
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                respuesta = SesionUserServer.flujoDatosEntradaUser.readBoolean();
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
            }else {
                Toast.makeText(getApplicationContext(),"No se ha podido insertar el nuevo Usuario",Toast.LENGTH_LONG).show();
            }
        }
    }





}





