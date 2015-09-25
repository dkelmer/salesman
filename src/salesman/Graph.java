package salesman;

import java.util.ArrayList;

public class Graph {
	double[][] adjacencyMatrix;
	ArrayList<City> cities;
	int numCities;
	
	public Graph() {
		cities = new ArrayList<City>();
	}
	
	public Graph(ArrayList<City> cities) {
		this.cities = new ArrayList<City>();
		for(City c : cities) {
			this.cities.add(c.clone());
		}
		this.numCities = cities.size();		
		initGraph();
		
	}
	
	private void initGraphBuffer() {
		adjacencyMatrix = new double[numCities+1][numCities+1];

		for(int i = 1; i < adjacencyMatrix.length-1; i++) {
			for(int j = i+1; j < adjacencyMatrix.length; j++) {
				double distance = cities.get(i-1).getDistance(cities.get(j-1));
				adjacencyMatrix[i][j] = distance;
				adjacencyMatrix[j][i] = distance;
			}
		}
		
		for(int i = 1; i < adjacencyMatrix.length; i++) {
			adjacencyMatrix[i][i] = Double.MAX_VALUE;
		}
		
		for(int i = 0; i < adjacencyMatrix.length; i++) {
        	for(int j = 0; j < adjacencyMatrix.length; j++) {
        		System.out.print(adjacencyMatrix[i][j] + " | ");
        	}
        	System.out.println();
        }
		
	}
	
	private void initGraph() {
		adjacencyMatrix = new double[numCities][numCities];

		for(int i = 0; i < adjacencyMatrix.length-1; i++) {
			for(int j = i+1; j < adjacencyMatrix.length; j++) {
				double distance = cities.get(i).getDistance(cities.get(j));
				adjacencyMatrix[i][j] = distance;
				adjacencyMatrix[j][i] = distance;
			}
		}
		
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			adjacencyMatrix[i][i] = Double.MAX_VALUE;
		}
		
		for(int i = 0; i < adjacencyMatrix.length; i++) {
        	for(int j = 0; j < adjacencyMatrix.length; j++) {
        		System.out.print(adjacencyMatrix[i][j] + " | ");
        	}
        	System.out.println();
        }
		
	}

}
