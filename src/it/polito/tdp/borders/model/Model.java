package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.GabowStrongConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Map<Integer,Country> IdcouMap;
	
	private Graph<Country,DefaultEdge> grafo;
	
	private Map<Country,Country> visita;
	

	public Model() {
		
		IdcouMap=new HashMap();
		grafo = new SimpleGraph<>(DefaultEdge.class);
		visita = new HashMap();
	
	}
	
	public void CreaoGrafo(int anno) {
		
		BordersDAO dao = new BordersDAO();
		this.riempiMappa(IdcouMap);
		System.out.println(IdcouMap.size());
		//aggiungo i vertici del grafo che sono il risultato della query.....
		
		Graphs.addAllVertices(grafo, dao.getCountryVertici(IdcouMap, anno));
		
		System.out.println("numero di vertici grafo "+grafo.vertexSet().size());
		//aggiungo gli archi 
		
		for(Country c : grafo.vertexSet()) {
			List<Country> confinanti = new ArrayList(dao.getConfine(anno, c.getCCode(), IdcouMap));
			for(Country confini : confinanti) {
				grafo.addEdge(c,confini);
			}
				
			
				
			
		}
		
		
	}
	
	public int trovagrado(Country paese ) {
		
		
		return grafo.degreeOf(paese);
		
	}
	
	public List<String> gradoPaese(){
		List<String> stampagradopaese = new  ArrayList<String>();
		for(Country c : grafo.vertexSet())
		{
			int grado=0;
			grado=this.trovagrado(c);
			String s = c.getStateNme()+" "+grado;
			stampagradopaese.add(s);
			
		}
		
		return stampagradopaese;
	}
	
	public int connettivita() {
		ConnectivityInspector <Country,DefaultEdge> coni = new ConnectivityInspector (this.grafo); 
		return coni.connectedSets().size();
		
	}
	
	public void riempiMappa(Map<Integer,Country> map) {
		BordersDAO dao = new BordersDAO();
		List<Country> listapaesi=new ArrayList<Country>(dao.loadAllCountries());
		for(Country c: listapaesi ) {
			map.put(c.getCCode(), c);
		}
	}
	
	public List<Country> nodiraggiungibili(int codicepartenza){
		Country partenza = IdcouMap.get(codicepartenza);
		
		BreadthFirstIterator<Country,DefaultEdge> it = new BreadthFirstIterator<Country, DefaultEdge> (this.grafo,partenza);
		
		List<Country> visitati = new  ArrayList<Country>();
		
		visitati.add(partenza);
		visita.put(partenza, null);
		
		
		while(it.hasNext()) {
			
			visitati.add(it.next());
			
		}
		
		return visitati;
		
	}
	
	public List<Country> nodiraggiungibiliiterativo(int codicepartenza){
		
		List<Country> Visitati = new LinkedList<Country>();
		
		List<Country> DaVisitare = new LinkedList<Country>();
		
		DaVisitare.add(IdcouMap.get(codicepartenza));//aggiungo in punto di partenza alla lista di visite
		for(Country c : DaVisitare) {
			if(c != null) {
				List<Country> temp =  new ArrayList<Country>(Graphs.neighborListOf(grafo, c));
				
				
				if(!Visitati.contains(c)) {//se il paese non è contenuto nella lista lo aggiungo
					Visitati.add(c);
					DaVisitare.remove(c);//e lo elimino dalla lista di quelli a da visitare visto che l'ho visitato
				}
				
				
				for(Country paese : temp) {
					if(!DaVisitare.contains(paese)) {
						DaVisitare.add(paese);
					}
					
				}
				
				
			}
		}
		
		return Visitati;
	}

	public Map<Integer, Country> getIdcouMap() {
		return IdcouMap;
	}

	public void setIdcouMap(Map<Integer, Country> idcouMap) {
		IdcouMap = idcouMap;
	}

	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph<Country, DefaultEdge> grafo) {
		this.grafo = grafo;
	}

	public Map<Country, Country> getVisita() {
		return visita;
	}

	public void setVisita(Map<Country, Country> visita) {
		this.visita = visita;
	}
	
	
	
	

}
