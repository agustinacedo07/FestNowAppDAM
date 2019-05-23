package com.example.agustin.festnowapp;


public class ControlErrores {


    private static final String ERR_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";



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

    //([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+


    public static boolean controlEmail(String email){

        if(email.matches(ERR_EMAIL)){
            return true;
        }
        return false;
    }




}
