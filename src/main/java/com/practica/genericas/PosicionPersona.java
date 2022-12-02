package com.practica.genericas;

import java.time.LocalDateTime;

import com.practica.excecption.EmsInvalidNumberOfDataException;


public class PosicionPersona {
	private Coordenada coordenada;
	private String documento;
	private LocalDateTime fechaPosicion;
	
	public PosicionPersona(String[] datos) throws EmsInvalidNumberOfDataException {
		if (datos.length != Constantes.MAX_DATOS_LOCALIZACION) {
			throw new EmsInvalidNumberOfDataException("El número de datos para LOCALIZACION es menor de 6");
		}
		
		this.documento = datos[1];
		this.fechaPosicion = FechaHora.parsearFechaHora(datos[2], datos[3]);
		this.coordenada = new Coordenada(Float.parseFloat(datos[4]), Float.parseFloat(datos[5]));
	}
	
	public PosicionPersona(Coordenada coordenada, String documento, LocalDateTime fechaPosicion) {
		this.coordenada = coordenada;
		this.documento = documento;
		this.fechaPosicion = fechaPosicion;
	}
	public Coordenada getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public LocalDateTime getFechaPosicion() {
		return fechaPosicion;
	}
	public void setFechaPosicion(LocalDateTime fechaPosicion) {
		this.fechaPosicion = fechaPosicion;
	}
	@Override
	public String toString() {
		String cadena = "";
        cadena += String.format("%s;", getDocumento());
        cadena += FechaHora.formatFecha(getFechaPosicion());
        cadena += String.format("%.4f;%.4f\n", getCoordenada().getLatitud(), 
	        		getCoordenada().getLongitud());
	
		return cadena;
	}
		
}
