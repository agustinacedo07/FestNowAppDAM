package com.example.agustin.festnowapp.Dialogos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agustin.festnowapp.R;
import com.example.agustin.festnowapp.Util.SesionUserServer;

import java.io.IOException;

import modelos.Comando;

/**
 * @author Agustin/Adrian
 * Clase que hace referencia a el cuadro de diálogo para realizar el cambio de contraseña del usuario para la actualizacion
 * recoge la contraseña antigua y la nueva
 */
@SuppressLint("ValidFragment")
public class DialogoUpdatePass extends DialogFragment{

    private Context contexto;
    private EditText etiPassNueva,etiPassVieja;


    public DialogoUpdatePass(Context contextoPadre){
        this.contexto = contextoPadre;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_update_pass,null);

        etiPassNueva = (EditText)view.findViewById(R.id.etiPassNueva);
        etiPassVieja = (EditText)view.findViewById(R.id.etiPassVieja);

        constructor.setView(view).setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(etiPassNueva.getText().toString().isEmpty() && etiPassVieja.getText().toString().isEmpty()){
                    Toast.makeText(contexto,"Los campos no pueden estar vacíos",Toast.LENGTH_LONG).show();
                }else{
                    new UpdatePass(etiPassVieja.getText().toString(),etiPassNueva.getText().toString()).execute();
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogoUpdatePass.this.getDialog().cancel();
            }
        });

        return constructor.create();
    }


    /**
     * Hilo que se ejecuta en segundo plano para realizar la comprobación de las contraseñas y su actualización si es posible
     */
    private class UpdatePass extends AsyncTask<Void,Void,Boolean>{
        private String antiguaPass;
        private String nuevaPass;

        public UpdatePass(String antiguaPass,String nuevaPass){
            this.antiguaPass = antiguaPass;
            this.nuevaPass = nuevaPass;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean operacion = false;
            Comando comando = new Comando();
            comando.setOrden("updatePass");
            comando.getArgumentos().add(SesionUserServer.clienteAplicacion.getIdCliente());
            comando.getArgumentos().add(antiguaPass);
            comando.getArgumentos().add(nuevaPass);

            try {
                SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
                operacion = SesionUserServer.flujoDatosEntradaUser.readBoolean();
            } catch (IOException e) {
                Toast.makeText(contexto,"Problema con la comunicación al Servidor",Toast.LENGTH_LONG).show();
            }
            return operacion;
        }

        @Override
        protected void onPostExecute(Boolean operacionUdapte) {
            if(operacionUdapte){
                Toast.makeText(contexto,"Contraseña actualizada",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(contexto,"No se ha podido actualizar la contraseña",Toast.LENGTH_LONG).show();
            }
        }
    }
}
