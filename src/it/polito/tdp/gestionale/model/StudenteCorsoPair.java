package it.polito.tdp.gestionale.model;

public class StudenteCorsoPair {

	private String codins;
	private int matricola;
	
	
	public StudenteCorsoPair(String codins, int matricola) {
		super();
		this.codins = codins;
		this.matricola = matricola;
	}


	public String getCodins() {
		return codins;
	}


	public void setCodins(String codins) {
		this.codins = codins;
	}


	public int getMatricola() {
		return matricola;
	}


	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codins == null) ? 0 : codins.hashCode());
		result = prime * result + matricola;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudenteCorsoPair other = (StudenteCorsoPair) obj;
		if (codins == null) {
			if (other.codins != null)
				return false;
		} else if (!codins.equals(other.codins))
			return false;
		if (matricola != other.matricola)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return codins + " " + matricola;
	}


	
	
	
}
