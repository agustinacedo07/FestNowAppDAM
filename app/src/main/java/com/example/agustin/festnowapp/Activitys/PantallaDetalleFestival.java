package com.example.agustin.festnowapp.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.agustin.festnowapp.Adaptadores.AdminFragmentAdapter;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;

import modelos.Comando;
import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase que muestra los detalles del Festival seleccionado de los que sigue el usuario
 */
public class PantallaDetalleFestival extends AppCompatActivity {

    private ViewPager view1;
    private Festival festival;
    private AdminFragmentAdapter adaptadorFragment;
    private Button btnAtras;
    //numero de pantallas de información que mostraremos
    public static int numPantallas = 6;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //obtenemos el festival que trae como argumento el itent lanzado
            festival = (Festival) getIntent().getExtras().get("festival");
            setContentView(R.layout.layout_detalle_festival);
            //cambio del nombre de la pantalla con el nombre del festival
            setTitle(festival.getNombre());

            btnAtras = (Button)findViewById(R.id.btnAtras);
            view1=(ViewPager) findViewById(R.id.view);

            adaptadorFragment = new AdminFragmentAdapter(getSupportFragmentManager(),festival,getApplicationContext());
            new UserComentFest(getApplicationContext(),view1,adaptadorFragment).execute();


            //funcionalidad del botón atrás
            btnAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pantallaFestivalesSeguidos = new Intent(getApplicationContext(),FestivalesSeguidos.class);
                    startActivity(pantallaFestivalesSeguidos);
                }
            });

    }


    /**
     * Hilo que se ejecuta en sgeundo plano que controla si el usuario ha comentado ya o no el festival, para mostrar o no el campo
     * de comentario y valoraciones
     */
    private class UserComentFest extends AsyncTask<Void,Void,Boolean>{
        private Context contexto;
        private ViewPager pantalla;
        private AdminFragmentAdapter adaptadorFragment;

        public UserComentFest(Context contexto,ViewPager pantalla,AdminFragmentAdapter adaptadorFragment){
            this.contexto = contexto;
            this.pantalla = pantalla;
            this.adaptadorFragment = adaptadorFragment;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean operacion = false;
            Comando comando = new Comando();
            comando.setOrden("userComentFest");
            comando.getArgumentos().add(SesionUserServer.clienteAplicacion.getIdCliente());
            comando.getArgumentos().add(festival.getIdFestival());
            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                operacion = SesionUserServer.flujoDatosEntradaUser.readBoolean();
            } catch (IOException e) {
                Toast.makeText(contexto,"Problema con la comunicación al Servidor",Toast.LENGTH_LONG).show();
            }

            return operacion;
        }


        @Override
        protected void onPostExecute(Boolean operacion) {
            if(operacion){
                PantallaDetalleFestival.numPantallas = 5;
            }else{
                PantallaDetalleFestival.numPantallas = 6;
            }

            pantalla.setAdapter(adaptadorFragment);
        }
    }


}
