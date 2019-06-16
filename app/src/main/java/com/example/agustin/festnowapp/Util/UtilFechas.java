package com.example.agustin.festnowapp.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String procesarFechaFestival(Date fechaInicio,Date fechaFin){
        String fechaProcesada = "";
        SimpleDateFormat fechaFormato = new SimpleDateFormat("dd-MM-yyyy");
        //procesar fecha de inicio
        String fechaInicioString = fechaFormato.format(fechaInicio);
        String fechaSeparada [] = fechaInicioString.split("-");
        fechaProcesada+=fechaSeparada[0]+" "+procesarMes(Integer.parseInt(fechaSeparada[1]))+" - ";

        //procesar fecha fin
        String fechaFinString = fechaFormato.format(fechaFin);
        fechaSeparada = fechaFinString.split("-");
        fechaProcesada+=fechaSeparada[0]+" "+procesarMes(Integer.parseInt(fechaSeparada[1]))+" del "+fechaSeparada[2];

        return fechaProcesada;


    }

    public static String procesarFechaConcierto(Date fechaConcierto){
        String fechaProcesada = "";

        SimpleDateFormat formatoFecha = new SimpleDateFormat("MM-dd");
        String [] tokensFecha = formatoFecha.format(fechaConcierto).split("-");
        fechaProcesada = tokensFecha[1]+" de "+procesarMes(Integer.parseInt(tokensFecha[0]));


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
