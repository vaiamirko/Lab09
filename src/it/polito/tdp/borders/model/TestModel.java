package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		System.out.println("Creo il grafo relativo al 2000");
		model.CreaoGrafo(2000);
		System.out.println("grafo creato");
		
		List<Country> countries = new ArrayList<Country>(model.getGrafo().vertexSet());
		
		System.out.format("Trovate %d nazioni\n", countries.size());

		System.out.format("Numero componenti connesse: %d\n", model.connettivita());
		
		System.out.println("paesi e gradi "+model.gradoPaese().toString());
		
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
