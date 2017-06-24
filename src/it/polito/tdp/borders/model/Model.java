package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	
	private SimpleGraph <Country, DefaultEdge> grafo;
	private List<Country> countries;
	private BordersDAO bordersDAO;
	private Map <Integer, Country> mappaCountry = new HashMap<Integer,Country>();
	private List<CountryVicini> lista;
	private List<CountryVicini> lista2;
	
	public Model (){
		bordersDAO = new BordersDAO();
	}
	
	
	public List<Country> getAllCountries(){
		if (countries==null){
			countries = bordersDAO.loadAllCountries();
			for (Country country : this.getAllCountries()){
				mappaCountry.put(country.getcCode(), country);
			}
		}
		
		
		return countries;
	}
	
	public String generaGrafo(int anno){
		
		grafo = new  SimpleGraph <Country, DefaultEdge> (DefaultEdge.class);
		
		//carico tutti i vertici
		Graphs.addAllVertices(grafo,this.getAllCountries());
		
		
		
		//carico tutti gli archi
		for (CountryPair cp : bordersDAO.getCoppieVicine(anno)){
			Country c1 = mappaCountry.get(cp.getN1());
			Country c2 = mappaCountry.get(cp.getN2());
			
			grafo.addEdge(c1,c2);
		}
		
		lista = new ArrayList<CountryVicini>();
		
		for (Country country : grafo.vertexSet()){
			int nconfini = Graphs.neighborListOf(grafo,country).size();
			if (nconfini!=0)
				lista.add(new CountryVicini(country, nconfini));
		}
		
		Collections.sort(lista);
		
		String s="";
		for (CountryVicini cv : lista){
			s+= cv.getCountry().getStateName()+" "+cv.getVicini()+"\n";
		}
		
		return s;
	}
	
	
	
	
	
	public List<CountryVicini> getLista() {
		return lista;
	}


	public void setLista(List<CountryVicini> lista) {
		this.lista = lista;
	}


	public static void main(String[] args) {
		Model m = new Model();
		
		
		System.out.println(m.generaGrafo(2000));
		
		System.out.println(m.generaGrafo(1900));
		
		
	}
	
	public int simula(Country country){
		
		Simulazione sim = new Simulazione(grafo);
		
		sim.addImmigrati(country);
		
		sim.run();
		
		lista2 = sim.getPresenti();
		
		return sim.getPassi();
		
	}


	public List<CountryVicini> getLista2() {
		return lista2;
	}
	
	

}
