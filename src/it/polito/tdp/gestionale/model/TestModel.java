package it.polito.tdp.gestionale.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		System.out.println(model.creaGrafo());
		
		System.out.println(model.getSequenzaCorsi());
		
		System.out.println(model.getCorsi().size());
		
	}

}
