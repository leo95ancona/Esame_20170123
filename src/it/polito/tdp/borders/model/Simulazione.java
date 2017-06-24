package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Simulazione {
	
	private int NUM_IMMIGRATI = 1000;
	private double PERC = 0.5;
	int passi;
	
	private SimpleGraph<Country,DefaultEdge> grafo;
	
	private Map<Country,Integer> mappa;
	
	private PriorityQueue<Event> queue;
	
	public Simulazione(SimpleGraph<Country,DefaultEdge> grafo){
		
		this.grafo = grafo;
		queue = new PriorityQueue<Event>();
		mappa = new HashMap<Country,Integer>();
		
		for (Country c : grafo.vertexSet()){
			mappa.put(c, 0);
		}
		
	}
	
	public void addImmigrati(Country country){
		Event e = new Event(NUM_IMMIGRATI, country, 1);
		queue.add(e);
	}
	
	public void run(){
		while (!queue.isEmpty()) {
			Event e = queue.poll();
			passi = e.getTime();
			
			//numero stanziati
			int stanziati = (int) (e.getNum()*PERC);
			
			//numero che partono in ogni paese
			int vicini = Graphs.neighborListOf(grafo, e.getCountry()).size();
			
			int viaggiatori = (int) (e.getNum()-stanziati)/(vicini);
			
			stanziati = e.getNum()-(viaggiatori*vicini);
			
			mappa.put(e.getCountry(), mappa.get(e.getCountry())+stanziati);
			
			if(stanziati>0){
				for(Country c : Graphs.neighborListOf(grafo, e.getCountry())){
					Event e2 = new Event(viaggiatori,c,e.getTime()+1);
					queue.add(e2);
				}
			}
		}
	}

	public int getPassi() {
		return passi;
	}
	
	public List<CountryVicini> getPresenti(){
		
		List<CountryVicini> lista = new ArrayList<CountryVicini>();
		
		for (Country c : mappa.keySet()){
			if (mappa.get(c)!=0){
				lista.add(new CountryVicini(c, mappa.get(c)) );
			}
		}
		
		Collections.sort(lista);
		
		return lista;
		
	}
		
		
	

}
