package com.practica.ems.covid;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.practica.genericas.Coordenada;
import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class ListaContactos {
	
	private TreeMap<LocalDateTime, GrupoCoordenadas> contactos;
	
	public ListaContactos() {
		this.contactos = new TreeMap<LocalDateTime, GrupoCoordenadas>();
	}
	
	public void addPersona(PosicionPersona pp) {
		GrupoCoordenadas go = null;
		if (contactos.containsKey(pp.getFechaPosicion())) {
			go = contactos.get(pp.getFechaPosicion());
		} else {
			go = new GrupoCoordenadas();
			contactos.put(pp.getFechaPosicion(), go);
		}
		go.addPersona(pp.getCoordenada());
	}
	
	public int personasEnCoordenadas () {
		GrupoCoordenadas go = contactos.get(contactos.firstKey());
		return go.getTotalPersonas();
	}
	
	public int tamanioLista () {
		return contactos.size();
	}

	public String getPrimerNodo() {
		LocalDateTime fecha = contactos.firstKey();
		return FechaHora.formatFecha(fecha);
	}

	/**
	 * Métodos para comprobar que insertamos de manera correcta en las listas de 
	 * coordenadas, no tienen una utilidad en sí misma, más allá de comprobar que
	 * nuestra lista funciona de manera correcta.
	 */
	public int numPersonasEntreDosInstantes(LocalDateTime inicio, LocalDateTime fin) {
		int nPersonas = 0;
		Collection<GrupoCoordenadas> setCoordenadas = contactos.subMap(inicio, true, fin, true).values();
		
		Iterator<GrupoCoordenadas> it = null;
		for (it = setCoordenadas.iterator(); it.hasNext();) {
			GrupoCoordenadas actualGo = it.next();
			nPersonas += actualGo.getTotalPersonas();
		}
		
		return nPersonas;
	}
	
	public int numNodosCoordenadaEntreDosInstantes(LocalDateTime inicio, LocalDateTime fin) {
		int nCoordenadas = 0;
		Collection<GrupoCoordenadas> setCoordenadas = contactos.subMap(inicio, true, fin, true).values();
		
		Iterator<GrupoCoordenadas> it = setCoordenadas.iterator();
		while (it.hasNext()) {
			GrupoCoordenadas actualGo = it.next();
			nCoordenadas += actualGo.getTotalCoordenadas();
		}
		
		return nCoordenadas;
	}
	
	@Override
	public String toString() {
		String cadena="";
		
		Iterator<LocalDateTime> it = contactos.keySet().iterator();
		while(it.hasNext()) {
			if (!cadena.isEmpty()) {
				cadena += " ";
			}
			cadena += FechaHora.formatFecha(it.next());
		}
		return cadena;
	}
	
	private class GrupoCoordenadas {
		
		private HashMap<Coordenada, Integer> coordenadas;
		private int totalPersonas;
		
		public GrupoCoordenadas() {
			coordenadas = new HashMap<Coordenada, Integer>();
			totalPersonas = 0;
		}
		
		public void addPersona(Coordenada coordenada) {
			if (coordenadas.containsKey(coordenada)) {
				Integer nPersonas = coordenadas.get(coordenada);
				coordenadas.put(coordenada, nPersonas.intValue() + 1);
			} else {
				coordenadas.put(coordenada, 1);
			}
			totalPersonas ++;
		}
		
		public int getTotalPersonas( ) {
			return totalPersonas;
		}
		
		public int getTotalCoordenadas() {
			return coordenadas.size();
		}
		
	}
}
