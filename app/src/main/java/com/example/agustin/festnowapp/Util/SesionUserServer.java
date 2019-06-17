package com.example.agustin.festnowapp.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import modelos.Cliente;

public class SesionUserServer {
    //atributos de comunicación con el servidor
    public static Socket skClienteUser;
    //flujo de datos
    public static DataInputStream flujoDatosEntradaUser;
    public static DataOutputStream flujoDatosSalidaUser;
    //flujo de objetos
    public static ObjectInputStream flujoEntradaObjetosUser;
    public static ObjectOutputStream flujoSalidaObjetosUser;



    //para que una vez se logue se cree una variable estática con todos los datos del cliente
    public static Cliente clienteAplicacion;
}
