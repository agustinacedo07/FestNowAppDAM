package com.example.agustin.festnowapp;


public class ControlErrores {


    public static boolean controlDni(String datos){
        String patron="[0-9]{8}[A-Z]{1}";
        if(datos.matches(patron))
            return true;
        else
            return false;
    }

    public static boolean controlTelefono(String datos){
        String patron="[6,9,7]{1}[0-9]{8}";
        if(datos.matches(patron))
            return true;
        else
            return false;
    }


}
