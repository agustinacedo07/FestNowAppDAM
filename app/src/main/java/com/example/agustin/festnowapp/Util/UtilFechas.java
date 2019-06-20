package com.example.agustin.festnowapp.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que contiene metodo para el procesamiento de formato de fechas
 */
public class UtilFechas {

    /**
     * Procesa la fecha seleccionada en el selector de fechas de el registro
     * @param dia
     * @param mes
     * @param anyo
     * @return
     */
    public static String valorarFechaPicker(int dia,int mes,int anyo){
        String fechaProcesada = "";
        fechaProcesada+=dia;
        //procesar mes
        fechaProcesada+=" de "+procesarMes(mes)+" ";
        //procesar anyo
        fechaProcesada+= " del "+anyo;

        return fechaProcesada;

    }

    /**
     * Procesa la fecha de un festival, obteniendo su fecha de inicio y de fin
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
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

    /**
     * Procesa la fecha de un concierto
     * @param fechaConcierto
     * @return
     */
    public static String procesarFechaConcierto(Date fechaConcierto){
        String fechaProcesada = "";

        SimpleDateFormat formatoFecha = new SimpleDateFormat("MM-dd");
        String [] tokensFecha = formatoFecha.format(fechaConcierto).split("-");
        fechaProcesada = tokensFecha[1]+" de "+procesarMes(Integer.parseInt(tokensFecha[0]));


        return fechaProcesada;
    }

    /**
     * Procesa la fecha al formato de una noticia
     * @param fechaNoticia
     * @return
     */
    public static String procesarFechaNoticia (Date fechaNoticia){
        String fechaProcesada = "";
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        String [] tokensFecha = formatoFecha.format(fechaNoticia).split("-");
        fechaProcesada = tokensFecha[0]+" del "+procesarMes(Integer.parseInt(tokensFecha[1]))+" de "+tokensFecha[2];

        return fechaProcesada;
    }


    /**
     * Procesa el mes del año según un numero de mes recibido
     * @param mes -numero del mes
     * @return - mes convertido a cadena de caracteres
     */
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
