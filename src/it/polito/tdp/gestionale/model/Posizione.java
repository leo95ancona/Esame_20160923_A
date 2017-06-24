package it.polito.tdp.gestionale.model;

public class Posizione {
	
	int valore;
	int posizione;
	public Posizione(int posizione, int valore) {
		super();
		this.valore = valore;
		this.posizione = posizione;
	}
	public int getValore() {
		return valore;
	}
	public void addValore() {
		this.valore++;
	}
	public int getPosizione() {
		return posizione;
	}
	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}
	@Override
	public String toString() {
		return valore+"";
	}
	
	

}
