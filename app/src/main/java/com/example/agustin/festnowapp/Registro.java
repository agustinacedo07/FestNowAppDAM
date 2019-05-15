package com.example.agustin.festnowapp;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import modelos.Cliente;
import modelos.Comando;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Registro extends AppCompatActivity implements View.OnClickListener {

    private EditText etiUsuario,etiPass,etiNombre,etiApellidos,etiFechaNacimiento,etiLocalidad,etiProvincia,etiComunidad,etiPais,etiCorreo,etiTelefono;
    private Button btnAceptarLogin;
    private Cliente nuevoUsuario;

    private static String APP_DIRECTORY="MyPictureApp/";
    private static String MEDIA_DIRECTORY=APP_DIRECTORY+"PictureApp";

    private final int MY_PERMISSION=100;
    private final int PHOTO_CODE=200;
    private final int SELECT_PICTURE=300;

    private ImageView mSetImage;
    private Button mOptionButton;
    private String mPath;
    private ConstraintLayout mRLView;


    //-----------------
    Button bfecha;
    EditText fechaNacimiento;
    private int dia, mes, ano;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        /* variables elegir foto y camara */

        bfecha=(Button)findViewById(R.id.bfecha);
        fechaNacimiento=(EditText) findViewById(R.id.fechaNacimiento);

        bfecha.setOnClickListener(this);

        //----------

        mRLView =(ConstraintLayout) findViewById(R.id.c);
        mSetImage=(ImageView) findViewById(R.id.fotoPerfil);
        mOptionButton=(Button) findViewById(R.id.bimagen);

        if(mayRequestStoragePermission()){
            mOptionButton.setEnabled(true);
        }else{
            mOptionButton.setEnabled(false);
        }

        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });



        /* ---- */







        etiUsuario=(EditText)findViewById(R.id.etiUsuario);
        etiPass=(EditText)findViewById(R.id.etiPass);
        etiNombre=(EditText)findViewById(R.id.etiNombre);
        etiApellidos=(EditText)findViewById(R.id.etiApellidos);
        etiFechaNacimiento=(EditText)findViewById(R.id.fechaNacimiento);
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


    /*

    PARTE MIA CON IMPLEMENTACION DE BOTONES Y DE SELECCION DE IMAGENES DE GALERIA, ETC.

     */


    private void showOptions() {
        //opciones del cuadro de dialogo
        final CharSequence[] option ={"Tomar foto","Elegir de galeria","Cancelar"};
        //cuadro de dialogo
        final AlertDialog.Builder builder=new AlertDialog.Builder(Registro.this);
        //configuramos el titulo
        builder.setTitle("Elige una opcion");
        //configuramos las opciones y le pasamos nuestro array
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //si es 0 toma foto, 1 galeria y 2 nada.
                if(option[which]=="Tomar foto"){
                    openCamera();
                }else if(option[which]=="Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("images/*");
                    //MANDA A LLAMAR A UNA APP A LA ESPERA DE LA RESPUESTA
                    startActivityForResult(intent.createChooser(intent,"Selecciona app de imagen"), SELECT_PICTURE);
                }else{
                    //cancelamos
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    private void openCamera() {
        //guarda la ruta del almacenamiento interno
        File file = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        //nos indicara si el dictorio esta creado o no
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated){
            isDirectoryCreated = file.mkdirs();
        }

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            //le damos el nombre de la fecha con segundosparaquenoserepita
            String imageName = timestamp.toString()+".jpg";
            mPath = Environment.getExternalStorageDirectory() +File.separator+ MEDIA_DIRECTORY+File.separator+imageName;

            File newFile=new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //enviar parametros entre actividades, vamos a enviarle un uri a la app
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }

    }

    //para salvr la varialbe path
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path",mPath);
    }

    //para restaurarla
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath=savedInstanceState.getString("file_path");

    }

    //aqui es donde van a caer todas las respuestas
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this, new String[]{mPath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage","Scanned"+path+":");
                            Log.i("ExternalStorage","->Uri="+uri);

                        }
                    });

                    //guardamos la ruta de la imagen, o imagenes
                    Bitmap bitmap=BitmapFactory.decodeFile(mPath);
                    //parameterla en el imagenview:
                    mSetImage.setImageBitmap(bitmap);
                    break;

                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mSetImage.setImageURI(path);
                    break;
            }
        }
    }

    private boolean mayRequestStoragePermission() {

        //verificar version menor 6
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        //si permisos aceptados true
        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        //si usuario ocupa un mensaje extra lo visualizamos en un snackbar, sino pide los permisos
        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))||(shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRLView, "Error", Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},MY_PERMISSION);
                }
            }).show();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},MY_PERMISSION);
        }

        return false;

    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v) {

        if(v==bfecha) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    fechaNacimiento.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                }
            }

                    ,dia,mes,ano);
            datePickerDialog.show();
        }






    }

    //respuesta
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==MY_PERMISSION){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            //explicacion de porq se necesitan los metodos
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Registro.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar esta funcion de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //abrira la configuracion para que el usuario habilite los permisos
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",getPackageName(),null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }


}
