package com.practica.ems.covid;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsInvalidNumberOfDataException;
import com.practica.excecption.EmsInvalidTypeException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.Constantes;
import com.practica.genericas.Coordenada;
import com.practica.genericas.FechaHora;
import com.practica.genericas.Persona;
import com.practica.genericas.PosicionPersona;

public class ContactosCovid {
	
	private static final HashSet<String> ELEMENTOS_VALIDOS = new HashSet<String>(){{
		add("PERSONA");
		add("LOCALIZACION");
	}};
	
	private Poblacion poblacion;
	private Localizacion localizacion;
	private ListaContactos listaContactos;

	public ContactosCovid() {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();
		this.listaContactos = new ListaContactos();
	}

	public Poblacion getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	public ListaContactos getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(ListaContactos listaContactos) {
		this.listaContactos = listaContactos;
	}
	
	public void loadDataFile(String fichero, boolean reset) throws EmsInvalidTypeException,
			EmsInvalidNumberOfDataException, EmsDuplicatePersonException, EmsDuplicateLocationException {

		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(Paths.get(fichero), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
			loadData(contentBuilder.toString(), reset);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadData(String data, boolean reset) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
			EmsDuplicatePersonException, EmsDuplicateLocationException {
		if (reset) {
			borrarListas();
		}
		
		String datas[] = dividirEntrada(data);
		for (String linea : datas) {
			procesarLinea(linea);
		}
	}
	
	private void borrarListas() {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();
		this.listaContactos = new ListaContactos();
	}
	
	private void procesarLinea(String linea) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
	EmsDuplicatePersonException, EmsDuplicateLocationException {
		String datos[] = this.dividirLineaData(linea);
		
		if (!ELEMENTOS_VALIDOS.contains(datos[0])) {
			throw new EmsInvalidTypeException();
		}
		
		if (datos[0].equals("PERSONA")) {
			parsePersona(datos);
		}
		
		if (datos[0].equals("LOCALIZACION")) {
			parseLocalización(datos);
		}
	}

	private void parseLocalización(String[] datos) throws EmsInvalidNumberOfDataException, EmsDuplicateLocationException {
		PosicionPersona pp = new PosicionPersona(datos);
		this.localizacion.addLocalizacion(pp);
		this.listaContactos.addPersona(pp);
	}
	
	private void parsePersona(String[] datos) throws EmsInvalidNumberOfDataException, EmsDuplicatePersonException {	
		Persona p = new Persona(datos);
		this.poblacion.addPersona(p);
	}

	
	public int findPersona(String documento) throws EmsPersonNotFoundException {
		return this.poblacion.findPersona(documento);
	}

	public int findLocalizacion(String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {

		int pos;
		try {
			pos = localizacion.findLocalizacion(documento, fecha, hora);
			return pos;
		} catch (EmsLocalizationNotFoundException e) {
			throw new EmsLocalizationNotFoundException();
		}
	}

	public List<PosicionPersona> localizacionPersona(String documento) throws EmsPersonNotFoundException {
		return this.localizacion.localizarPersona(documento);
	}

	public void delPersona(String documento) throws EmsPersonNotFoundException {
		this.poblacion.delPersona(documento);
	}

	private String[] dividirEntrada(String input) {
		String cadenas[] = input.split("\\n");
		return cadenas;
	}

	private String[] dividirLineaData(String data) {
		String cadenas[] = data.split("\\;");
		return cadenas;
	}
}
