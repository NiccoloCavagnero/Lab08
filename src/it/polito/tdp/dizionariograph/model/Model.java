package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private WordDAO dao = new WordDAO();
	private List<String> listaParole;
	private Graph<String,DefaultEdge> grafo;

	public void createGraph(int numeroLettere) {
		
	listaParole = new LinkedList<String>(dao.getAllWordsFixedLength(numeroLettere));
	grafo = new SimpleGraph<String,DefaultEdge>(DefaultEdge.class);
	
	Graphs.addAllVertices(grafo, listaParole);
	
	int count;
	
	for ( String s : listaParole ) {
		for ( String r : listaParole ) {
			if ( !grafo.containsEdge(s, r) ) {
				
				count = 0;
				
				char[] array1 = s.toCharArray();
				char[] array2 = r.toCharArray();
				
				for ( int i=0; i<numeroLettere; i++) {
					if ( array1[i]!=array2[i] ) 
						count++;
					if (count>1)
						break;
					
				}
				if ( count == 1 )
					grafo.addEdge(s, r);
			}
		}
	}
	
		System.out.println("Grafo creato!");
	}

	public List<String> displayNeighbours(String parolaInserita) {

		List<String> listaVicini = new ArrayList<String>();
		
		for ( String s : listaParole ) {
			if (grafo.containsEdge(parolaInserita, s))
				listaVicini.add(s);
		}
		return listaVicini;
	}

	public int findMaxDegree() {
		
		int maxDegree=0;
		
		for (String s : listaParole) {
			if (displayNeighbours(s).size()>maxDegree) 
				maxDegree=displayNeighbours(s).size();
		}
		return maxDegree;
	}
}
