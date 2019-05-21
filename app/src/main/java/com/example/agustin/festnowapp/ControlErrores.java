package com.example.agustin.festnowapp;


public class ControlErrores {



    public static boolean controlTelefono(String datos){
        String patron="[6,9,7]{1}[0-9]{8}";
        if(datos.matches(patron))
            return true;
        else
            return false;
    }

    public static boolean controlCaracter(String datos){
        String patron="([a-z]|[A-Z]|\\s)+";
        if(datos.matches(patron)) {
            return true;
        }
        return false;
    }


}
