package com.example.agustin.festnowapp.Dialogos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agustin.festnowapp.Activitys.PantallaPerfil;
import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;

import modelos.Comando;

/**
 * @author Agustin/Adrian
 * Clase que hace referencia al cuadro de diálogo para cambiar el usuario
 */
@SuppressLint("ValidFragment")
public class DialogoUpdateUser extends DialogFragment {

    private Context contexto;
    private EditText txtNuevoUsuario;

    public DialogoUpdateUser(Context contextoPadre){
        this.contexto = contextoPadre;
    }


    /**
     * Metodo dar el estilo y la funcionalidad al botón
     * @param savedInstanceState
     * @return - retorna el cuadro de diálogo ya creado listo para ser llamado y mostrado
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder contructor = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_update_user,null);

        txtNuevoUsuario = (EditText)view.findViewById(R.id.etiNuevoUsuario);

        contructor.setView(view).setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {//boton aceptar
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(txtNuevoUsuario.getText().toString().equals("")){
                    Toast.makeText(contexto,"Campo vacío",Toast.LENGTH_LONG).show();
                }else{
                    new UpdateUser(txtNuevoUsuario.getText().toString()).execute();
                }

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {//boton cancelar
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogoUpdateUser.this.getDialog().cancel();
            }
        });

        return  contructor.create();
    }


    /**
     * Hilo que se ejecuta en segundo plano que realiza las consultas y actualizaciones del usuario para cambiar el nombre
     *
     */
    private class UpdateUser extends AsyncTask<Void,Void,Boolean>{
        private String nuevoUsuario;

        public UpdateUser(String nuevoUsuario ){
            this.nuevoUsuario = nuevoUsuario;

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean operacion = false;
            Comando comando = new Comando();
            comando.setOrden("updateUser");
            comando.getArgumentos().add(nuevoUsuario);
            comando.getArgumentos().add(SesionUserServer.clienteAplicacion.getIdCliente());

            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                operacion = SesionUserServer.flujoDatosEntradaUser.readBoolean();
            } catch (IOException e) {
                Toast.makeText(contexto,"Problema de comunicación con el servidor",Toast.LENGTH_LONG).show();
            }
            return operacion;
        }

        @Override
        protected void onPostExecute(Boolean respuesta) {
            if(respuesta){
                Toast.makeText(contexto,"Usuario modificado con éxito",Toast.LENGTH_LONG).show();
                SesionUserServer.clienteAplicacion.setUsuario(nuevoUsuario);
                Intent actualizarPantalla = new Intent(contexto, PantallaPerfil.class);
                contexto.startActivity(actualizarPantalla);
            }else{
                Toast.makeText(contexto,"El usuario -"+nuevoUsuario+" ya existe",Toast.LENGTH_LONG).show();
            }
        }
    }
}
