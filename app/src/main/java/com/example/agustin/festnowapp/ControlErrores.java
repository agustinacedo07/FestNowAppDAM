package com.example.agustin.festnowapp;


import android.icu.util.LocaleData;

import com.example.agustin.festnowapp.Util.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ControlErrores {
    //datos que recibirá que queremos validar
    private String [] datos;
    //para informar si la validación general es o no correcta
    private boolean validacion = true;
    //conjunto de campos tras su validacion
    ArrayList<Validator> listaValidator;




    public ControlErrores(String [] datos){
        this.datos = datos;
        listaValidator = new ArrayList<Validator>();
        validarCampos();

    }



    public void validarCampos(){
        Validator validator = new Validator();
        //validacion de nombre
        if(controlNombre(datos[0])){
            validacion = false;
            validator.setCampo("Nombre");
            validator.setProblemaValidacion("El nombre no puede ser numérico");
            validator.setValidacionCampo(false);
        }else{
            validator.setValidacionCampo(true);
            validator.setCampo("Nombre");
        }

        listaValidator.add(validator);

        //validacion de apellidos
        if(controlNombre(datos[1])){
            validacion = false;
            validator.setCampo("Apellido");
            validator.setProblemaValidacion("El apellido no puede ser numérico");
        }else{
            validator.setCampo("Apellido");
            validator.setValidacionCampo(true);
        }

        listaValidator.add(validator);

        //validacion de fecha de nacimiento
        validator = controlFecha(datos[2]);
        listaValidator.add(validator);

        //validacion de localidad
        validator = controlLocalidad(datos[3]);
        listaValidator.add(validator);


        //validacion de provincia
        validator = controlProvincia (datos[4]);
        listaValidator.add(validator);


        //validacion de comunidad
        validator = controlComunidad(datos[5]);
        listaValidator.add(validator);

        //validacion de pais
        validator = controlPais(datos[6]);
        listaValidator.add(validator);

        //validacion de mail
        validator = controlEmail(datos[7]);
        listaValidator.add(validator);

        //validacion de telefono
        validator = controlTelefono(datos[8]);
        listaValidator.add(validator);

        //validacion de nombre de usuario
        validator = controlUser(datos[9]);
        listaValidator.add(validator);


        //validacion de pass de usuario
        validator = controlPass(datos[10]);
        listaValidator.add(validator);

    }


    /**
     * Comprueba si el nombre es numérico
     * @param nombre - nombre que introduce el usuario
     * @return - booelano con true si es numérico o false si no es numérico
     */
    public boolean controlNombre(String nombre){
        try{
            Integer.parseInt(nombre);
            return  true;
        }catch (NumberFormatException e){
            return false;
        }
    }


    public Validator controlLocalidad(String localidad){
        Validator validator = new Validator();
        validator.setCampo("Localidad");
        validator.setValidacionCampo(true);

        //validar localidad
        return validator;
    }

    public Validator controlProvincia(String provincia){
        Validator validator = new Validator();
        validator.setCampo("Provincia");
        validator.setValidacionCampo(true);

        //validar provincia
        return validator;
    }


    public Validator controlComunidad(String comunidad){
        Validator validator = new Validator();
        validator.setCampo("Comunidad");
        validator.setValidacionCampo(true);


        //validar comunidad
        return  validator;

    }

    public Validator controlPais (String pais){
        Validator validator = new Validator();
        validator.setCampo("Pais");
        validator.setValidacionCampo(true);


        //validacion pais
        return  validator;
    }


    public Validator  controlEmail(String email){
        Validator validator = new Validator();
        validator.setCampo("E-Mail");
        validator.setValidacionCampo(true);
        final String ERR_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        //control de formato de mail
        if(!email.matches(ERR_EMAIL)){
            validacion = false;
            validator.setProblemaValidacion("Formato incorrecto de mail");
            validator.setValidacionCampo(false);
            return  validator;
        }

        //validacion de campo mail en la BD ---- FALTA IMPLEMENTAR


        return validator;
    }


    public Validator controlTelefono(String datos){
        Validator validator = new Validator();
        validator.setCampo("Teléfono");
        validator.setValidacionCampo(true);
        String patron="[6,9,7]{1}[0-9]{8}";

        //validacion de formato teléfono
        if(!datos.matches(patron)){
            validacion = false;
            validator.setValidacionCampo(false);
            validator.setProblemaValidacion("Formato incorrecto");
            return validator;
        }

        return validator;


    }


    public Validator controlFecha (String fecha){
        //preparamos un validator por defecto
        Validator validator = new Validator();
        validator.setCampo("Fecha Nacimiento");
        validator.setValidacionCampo(true);

        //construimos la fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formatoFecha.parse(fecha);
        } catch (ParseException e) {
            //controlar la excepcion
            e.printStackTrace();
        }
        //comprobación que no sea superior a la actual
        int comparador = fechaNacimiento.compareTo(new Date());
        if(comparador>0 ){
            validacion = false;
            validator.setValidacionCampo(false);
            validator.setProblemaValidacion("La fecha no puede ser superior a la actual");
            return validator;
        }

        if(comparador==0){
            validacion = false;
            validator.setProblemaValidacion("La fecha no puede ser la actual");
            validator.setValidacionCampo(false);
        }



        //comprobación que sea mínimia hace diciocho años
        Calendar fechaNacimientoCalendar = Calendar.getInstance();
        Calendar fechaActualCalendar = Calendar.getInstance();

        fechaNacimientoCalendar.setTime(fechaNacimiento);

        int anyo = fechaActualCalendar.get(Calendar.YEAR)- fechaNacimientoCalendar.get(Calendar.YEAR);
        int mes = fechaActualCalendar.get(Calendar.MONTH) - fechaActualCalendar.get(Calendar.MONTH);
        int dia = fechaActualCalendar.get(Calendar.DATE) - fechaNacimientoCalendar.get(Calendar.DATE);

        //ajuste de año
        if(mes<0 || (mes ==0 && dia<0)){
            anyo--;
        }

        if(anyo<18){
            validacion = false;
            validator.setValidacionCampo(false);
            validator.setProblemaValidacion("Debe ser mayor de 18 años");
            return  validator;
        }


        return validator;

    }


    public Validator controlUser (String usuario){
        Validator validator = new Validator();
        validator.setCampo("Usuario");
        validator.setValidacionCampo(true);


        //validacion de usuario en la BD --- FALTA POR IMPLEMENTAR


        return  validator;
    }

    public Validator controlPass(String pass){
        Validator validator =new Validator();
        validator.setCampo("Contraseña");
        validator.setValidacionCampo(true);

        //validacion de la contraseña en la BD ---- FALTA POR IMPLEMENTAR
        return validator;
    }


    public String[] getDatos() {
        return datos;
    }

    public void setDatos(String[] datos) {
        this.datos = datos;
    }

    public boolean isValidacion() {
        return validacion;
    }

    public void setValidacion(boolean validacion) {
        this.validacion = validacion;
    }

    public ArrayList<Validator> getListaValidator() {
        return listaValidator;
    }

    public void setListaValidator(ArrayList<Validator> listaValidator) {
        this.listaValidator = listaValidator;
    }
}
