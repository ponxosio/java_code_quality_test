package com.practica.ems.covid;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.FechaHora;
import com.practica.genericas.Persona;

public class Poblacion {
	LinkedList<Persona> lista ;

	public Poblacion() {
		super();
		this.lista = new LinkedList<Persona>();
	}
	
	public List<Persona> getLista() {
		return lista;
	}

	public void setLista(Collection<Persona> lista) throws EmsDuplicatePersonException {
		this.lista = new LinkedList<Persona>();
		for (Iterator<Persona> it = lista.iterator(); it.hasNext();) {
			addPersona(it.next());
		}
	}

	public void addPersona (Persona persona) throws EmsDuplicatePersonException {
		if (lista.contains(persona)) {
			throw new EmsDuplicatePersonException();
		}
		lista.add(persona);
	}
	
	public void delPersona(String documento) throws EmsPersonNotFoundException {
		int pos = findPersona(documento);
		lista.remove(pos);
	}
	
	public int findPersona (String documento) throws EmsPersonNotFoundException {
		int cont=0;
		Iterator<Persona> it = lista.iterator();
		while (it.hasNext() ) {
			Persona persona = it.next();
			if(persona.getDocumento().equals(documento)) {
				return cont;
			}
			cont++;
		}		
		throw new EmsPersonNotFoundException();
	}
	
	public void printPoblacion() {
		System.out.printf(toString());
	}

	@Override
	public String toString() {
		String cadena = "";
		for(int i = 0; i < lista.size(); i++) {
			cadena += String.format("%s\n", lista.get(i).toString());	    		        
		}
		return cadena;
	}
	
	
	
}
