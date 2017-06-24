package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {

	private List<Corso> corsi;
	private Map <String,Corso> mappaCorsi;
	private List<Studente> studenti;
	private Map <Integer,Studente> mappaStudenti;
	private DidatticaDAO didatticaDAO;
	private SimpleGraph <Nodo, DefaultEdge> grafo;
	private List<Corso> migliore;
	private int i =0;

	public Model() {
		didatticaDAO = new DidatticaDAO();
		mappaCorsi = new HashMap<String,Corso>();
		mappaStudenti = new HashMap <Integer,Studente>();
	}
	
	
	public List<Studente> getStudenti(){
		if (studenti==null){
			studenti = didatticaDAO.getTuttiStudenti();
			for(Studente studente : this.getStudenti()){
				mappaStudenti.put(studente.getMatricola(), studente);
			}
		}
		
		
		return studenti;
	}
	
	
	public List<Corso> getCorsi(){
		if (corsi==null){
			corsi=didatticaDAO.getTuttiICorsi();
			for(Corso corso : this.getCorsi()){
				mappaCorsi.put(corso.getCodins(), corso);
			}
			
			getStudenti();
			for (Corso corso : corsi) {
				didatticaDAO.setStudentiIscrittiAlCorso(corso, mappaStudenti);
			}
		}
		
		return corsi;
	}
	
	
	public Map creaGrafo(){
		
		grafo = new SimpleGraph<Nodo, DefaultEdge>(DefaultEdge.class);
		
		//aggiungo i nodi
		this.getStudenti();
		this.getCorsi();
		
		Graphs.addAllVertices(grafo, this.getStudenti());
		System.out.println("Studenti #: "+this.getStudenti().size());
		
		
		Graphs.addAllVertices(grafo, this.getCorsi());
		System.out.println("Corsi #: "+this.getCorsi().size());
		
		System.out.println("Vertici grafo #: "+grafo.vertexSet().size());
		
		
		//aggiungo gli archi
		for (StudenteCorsoPair scp : didatticaDAO.getStudentiIscrittiAlCorso(mappaStudenti,mappaCorsi)){
			
			Studente studente = mappaStudenti.get(scp.getMatricola());
			Corso corso = mappaCorsi.get(scp.getCodins());
			
			grafo.addEdge(studente, corso);
		}
		
		Map<Integer,Posizione> mappa = new HashMap<Integer,Posizione>();
		for (int i=0; i<this.getCorsi().size()+1; i++){
			mappa.put(i, new Posizione(i,0));
		}
		
		for (Studente studente : this.getStudenti()){
			int posizione = Graphs.neighborListOf(grafo, studente).size();
			
			Posizione p = mappa.get(posizione);
			p.addValore();
			
		}
		
		return mappa;
		
	}
	
	public List<Corso> getSequenzaCorsi(){
		
		List<Corso> soluzioneParziale = new ArrayList<Corso>();
		List<Corso> soluzioneMigliore = new ArrayList<Corso>();
		
		recursive(soluzioneParziale, soluzioneMigliore);
		
		return soluzioneMigliore;
	}


	private void recursive(List<Corso> soluzioneParziale, List<Corso> soluzioneMigliore) {
		
		HashSet<Studente> hashSetStudenti = new HashSet<Studente>(getStudenti());
		for (Corso corso : soluzioneParziale) {
			hashSetStudenti.removeAll(corso.getStudenti());
		}
		
		if (hashSetStudenti.isEmpty()) {
			if (soluzioneMigliore.isEmpty())
				soluzioneMigliore.addAll(soluzioneParziale);
			if (soluzioneParziale.size() < soluzioneMigliore.size()){
				soluzioneMigliore.clear();
				soluzioneMigliore.addAll(soluzioneParziale);
			}
		}
		
		//System.out.println(soluzioneParziale);
		for (Corso corso : getCorsi()) {
			if (soluzioneParziale.isEmpty() || corso.compareTo(soluzioneParziale.get(soluzioneParziale.size()-1)) > 0) {
				soluzioneParziale.add(corso);
				recursive(soluzioneParziale, soluzioneMigliore);
				soluzioneParziale.remove(corso);
			}
		}
		
	}
		
		
		
	
	
	
	
}
