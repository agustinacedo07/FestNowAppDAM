package com.example.agustin.festnowapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Comando implements Serializable {
	private String orden;
	private ArrayList<Object> argumentos = new ArrayList<>();
	private Object respuestaObjecto;
	private boolean respuestaBooleana;
	
	
	
	
	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public ArrayList<Object> getArgumentos() {
		return argumentos;
	}
	public void setArgumentos(ArrayList<Object> argumentos) {
		this.argumentos = argumentos;
	}
	public Object getRespuestaObjecto() {
		return respuestaObjecto;
	}
	public void setRespuestaObjecto(Object respuestaObjecto) {
		this.respuestaObjecto = respuestaObjecto;
	}
	public boolean isRespuestaBooleana() {
		return respuestaBooleana;
	}
	public void setRespuestaBooleana(boolean respuestaBooleana) {
		this.respuestaBooleana = respuestaBooleana;
	}
	
	
	
	
	
	
	

}
