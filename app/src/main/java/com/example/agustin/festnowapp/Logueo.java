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

                // Aquí llamamos a nuestro archivo .php creado anteriormente. (consulta.php)
                // Realizo un consultar cuando el id=al valor que introduce el usuario en el editext id.

                String nombreUsu = usuario.getText().toString();
                String nombrePass = contraseña.getText().toString();


                //con get
                //new ConsultarDatos().execute("http://10.0.2.2/CursoAndroid/consulta.php?id="+etId.getText().toString());
                //con post
                //new MainActivity.login().execute("http://10.0.2.2/login/consultaLogin.php?nombre="+nombre);
                new login().execute("http://10.0.2.2/RUTA AGU RUTA AGU RUTA AGU" + nombreUsu + "&password=" + nombrePass);


            }
        });


    }

    @Override
    public void onClick(View v) {

        Intent REGIS = new Intent(getApplicationContext(), Registro.class);
        startActivity(REGIS);
    }


    private class login extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Cojo cada uno de los valores recibidos y los muestro en cada uno de los ediText de la aplicación móvil.
            // Me creo un JSONArray.
            String ex = "";

            /*

             EN MENU. CLASS IRIA LA LLAMADA A LA VENTANA PRINCIPAL. CUANDO ESTE IMPLMENTARLO

            if (result.contains("true")) {
                try {
                    Toast.makeText(getApplicationContext(), "¡Correcto!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Menu.class);

                    startActivity(intent);
                }catch(Exception e){
                    ex=e.toString();
                }

            } else {
                Toast.makeText(getApplicationContext(), "El usuario o la contraseña no existe", Toast.LENGTH_LONG).show();
            }

*/
        }


        private String downloadUrl(String myurl) throws IOException {
            Log.i("URL", "" + myurl);
            myurl = myurl.replace(" ", "%20"); // Es muy importante utilizar este método
            // que reemplaza los espacios por %20.
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET"); // Metodo en el que vamos a enviar los datos. En nuestro caso GET.
                conn.setDoInput(true);
                // Starts the query
                conn.connect(); // Aquí es donde se realiza la conexión.
                int response = conn.getResponseCode();
                Log.d("respuesta", "The response is: " + response);
                is = conn.getInputStream(); // Lo que responda la URL lo guarda en la variable is.

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len); // Llamamos al método readIt
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        // Método que convierte un InputStream a un String.
        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

    }
}
