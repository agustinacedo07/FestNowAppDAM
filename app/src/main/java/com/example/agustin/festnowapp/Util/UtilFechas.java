package com.example.agustin.festnowapp.Util;

public class UtilFechas {

    public static String valorarFechaPicker(int dia,int mes,int anyo){
        String fechaProcesada = "";
        fechaProcesada+=dia;
        //procesar mes
        fechaProcesada+=" de "+procesarMes(mes)+" ";
        //procesar anyo
        fechaProcesada+= " del "+anyo;

        return fechaProcesada;

    }


    private static String procesarMes(int mes){
        String mesCadena = "";
        switch (mes){
            case 1:
                mesCadena = "Enero";
                break;
            case 2:
                mesCadena = "Febrero";
                break;
            case 3:
                mesCadena = "Marzo";
                break;
            case 4:
                mesCadena = "Abril";
                break;
            case 5:
                mesCadena = "Mayo";
                break;
            case 6:
                mesCadena = "Junio";
                break;
            case 7:
                mesCadena = "Julio";
                break;
            case 8:
                mesCadena = "Agosto";
                break;
            case 9:
                mesCadena = "Septiembre";
                break;
            case 10:
                mesCadena = "Octubre";
                break;
            case 11:
                mesCadena = "Noviembre";
                break;
            case 12:
                mesCadena = "Diciembre";
                break;
        }

        return mesCadena;
    }
}
