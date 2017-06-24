package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{
	
	private int num;
	private Country country;
	private int time;
	
	public Event(int num, Country country, int time) {
		super();
		this.num = num;
		this.country = country;
		this.time = time;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.getTime()-o.getTime();
	}
	
	
	
}
