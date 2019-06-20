package com.example.agustin.festnowapp.Util;

/**
 * Clase de objeto modelo, para el procesamiento y análisis de determinados campos en su control de errores
 */
public class Validator {
    //indica el tipo de campo que se ha validado
    private String campo;
    //indica si el campo está o no validado
    private boolean validacionCampo;
    //indica si no está validado el problema de la validación
    private String problemaValidacion;


    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public boolean isValidacionCampo() {
        return validacionCampo;
    }

    public void setValidacionCampo(boolean validacionCampo) {
        this.validacionCampo = validacionCampo;
    }

    public String getProblemaValidacion() {
        return problemaValidacion;
    }

    public void setProblemaValidacion(String problemaValidacion) {
        this.problemaValidacion = problemaValidacion;
    }
}
