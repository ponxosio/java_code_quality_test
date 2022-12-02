package com.practica.genericas;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FechaHora {
	
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
	public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm");

	public static LocalDateTime parsearFechaHora(final String fecha, final String hora) {
		return LocalDateTime.parse(fecha + ";" + hora, DATETIME_FORMAT);
	}

	public static LocalDate parsearFecha(final String fecha) {
		return LocalDate.parse(fecha, DATE_FORMAT);
	}
	
	public static String formatFecha(final LocalDate fecha) {
		return fecha.format(DATE_FORMAT);
	}
	
	public static String formatFecha(final LocalDateTime fecha) {
		return fecha.format(DATETIME_FORMAT);
	}
	
	public static String getFecha(final LocalDateTime fecha) {
		return fecha.format(FechaHora.DATE_FORMAT);
	}
	
	public static String getHora(final LocalDateTime fecha) {
		return fecha.format(FechaHora.TIME_FORMAT);
	}
	
}
