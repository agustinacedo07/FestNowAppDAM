package com.example.agustin.festnowapp.Util;

import android.os.AsyncTask;

import com.example.agustin.festnowapp.Constantes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import modelos.Cliente;

public class SesionServer extends AsyncTask<Void,Void,Boolean> {
    //atributos de comunicación con el servidor
    public static Socket skCliente;
    //flujo de datos
    public static DataInputStream flujoDatosEntrada;
    public static DataOutputStream flujoDatosSalida;
    //flujo de objetos
    public static ObjectInputStream flujoEntradaObjetos;
    public static ObjectOutputStream flujoSalidaObjetos;



    //para que una vez se logue se cree una variable estática con todos los datos del cliente
    public static Cliente clienteAplicacion;



    @Override
    protected Boolean doInBackground(Void... voids) {
        return abrirFlujos();
    }


    /**
     * Abre los flujos de comunicación con el servidor
     * @return - un booleano indicando si la conexion se ha realizado o no
     */
    private boolean abrirFlujos() {
        try {

            skCliente = new Socket(Constantes.IP_CONEXION,1990);


            InputStream entrada = skCliente.getInputStream();
            OutputStream salida = skCliente.getOutputStream();


            flujoSalidaObjetos = new ObjectOutputStream(salida);
            flujoEntradaObjetos = new ObjectInputStream(skCliente.getInputStream());
            flujoDatosSalida = new DataOutputStream(salida);
            flujoDatosEntrada = new DataInputStream(entrada);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }
}
