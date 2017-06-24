package it.polito.tdp.borders.model;

import java.util.Comparator;

public class CountryVicini implements Comparable<CountryVicini> {
	
	
	private Country country;
	private int vicini;
	
	
	public CountryVicini(Country country, int vicini) {
		super();
		this.country = country;
		this.vicini = vicini;
	}


	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}


	public int getVicini() {
		return vicini;
	}


	public void setVicini(int vicini) {
		this.vicini = vicini;
	}


	@Override
	public String toString() {
		return country +" "+ vicini;
	}


	@Override
	public int compareTo(CountryVicini o) {
		
		return -(this.vicini-o.vicini);
	}
	
	
}
