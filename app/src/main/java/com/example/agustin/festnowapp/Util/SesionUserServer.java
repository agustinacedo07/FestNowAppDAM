package com.example.agustin.festnowapp.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import modelos.Cliente;

/**
 * Clase que guarda todas las variables que se usarán a modo de sesión durante la ejecución del programa
 */
public class SesionUserServer {
    //****************** VARIABLES DE COMUNICACION CON EL SERVIDOR ******************
    //atributos de comunicación con el servidor
    public static Socket skClienteUser;
    //flujo de datos
    public static DataInputStream flujoDatosEntradaUser;
    public static DataOutputStream flujoDatosSalidaUser;
    //flujo de objetos
    public static ObjectInputStream flujoEntradaObjetosUser;
    public static ObjectOutputStream flujoSalidaObjetosUser;

    //******************** CLIENTE QUE INICIA SESION ***************************
    //para que una vez se logue se cree una variable estática con todos los datos del cliente
    public static Cliente clienteAplicacion ;
}
