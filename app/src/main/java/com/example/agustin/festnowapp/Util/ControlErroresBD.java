package com.example.agustin.festnowapp.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.agustin.festnowapp.ControlErroresRegistro;

import java.io.IOException;

import modelos.Comando;

public class ControlErroresBD extends AsyncTask<Void,Boolean,Boolean>{
    String tipoDatoValidar;
    String datoValidar;
    CallBackControlErroresBD oyente;


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
