package com.example.agustin.festnowapp.Util;

import android.os.AsyncTask;

import java.io.IOException;

import modelos.Comando;

/**
 * Clase que realiza control de errores realacionados con campos guardados en la BD que son importantes a la hroa de registro, que no
 * se encuentren repetidos o mal formados
 */
public class ControlErroresBD extends AsyncTask<Void,Boolean,Boolean>{
    private String tipoDatoValidar;
    private String datoValidar;
    private CallBackControlErroresBD oyente;


    public ControlErroresBD(String tipoDato, String dato, CallBackControlErroresBD oyente){
        this.tipoDatoValidar = tipoDato;
        this.datoValidar = dato;
        this.oyente = oyente;



    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        boolean validacionCampo = false;
        Comando comando = new Comando();
        comando.getArgumentos().add(datoValidar);
        //comprueba que tipo de campo se va a realizar la comprobacion de los ubicados en el registro
        switch (tipoDatoValidar){
            case "usuario":
                comando.setOrden("isUser");
                break;
            case "mail":
                comando.setOrden("isMail");
                break;
            case "pass":
                comando.setOrden("isPass");
                break;
        }

        try {
            SesionUserServer.flujoSalidaObjetosUser.writeObject(comando);
            validacionCampo = SesionUserServer.flujoDatosEntradaUser.readBoolean();
        } catch (IOException e) {
            validacionCampo = false;
            return validacionCampo;
        }
        return validacionCampo;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        oyente.devolverRespuesta(aBoolean,tipoDatoValidar);
    }


}
