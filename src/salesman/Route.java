package salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Route {
	int id;
	ArrayList<City> cities;
	double totalDistance;
	
	public Route() {
		this.cities = new ArrayList<City>();
	}
	
	public void addCity(City c) {
		cities.add(c);
	}
	
	public double calculateTotalPathLength() {
		double currLength = 0;
		for(int i = 0; i < cities.size()-1; i++) {
			double dist = cities.get(i).getDistance(cities.get(i+1));
			currLength += dist;
			//System.out.printf("distance b/w city #%d and city #%d is: %f\n", (i+1), (i+2), dist);
		}
		this.totalDistance = currLength;
		return currLength;
		
		/**
		 * idea for optimization: store distances in chunks in an array
		 * (i.e. cities 1-10, 11-20 etc etc) that way when we swap cities, 
		 * only need to recalculate for at most two small chunks
		 */
	}
	
	public void generateNeighbor() {
		//in current implementation, swap two random cities
		Random random = new Random();
		int city1idx = random.nextInt(1000);
		int city2idx = city1idx;
		while(city2idx == city1idx) {
			city2idx = random.nextInt(1000);
		}
		
		swapCities(city1idx, city2idx);
		calculateTotalPathLength();
		
	}
	
	public void swapCities(int city1idx, int city2idx) {
		City city1 = cities.get(city1idx);
		City city2 = cities.get(city2idx);
		
		cities.set(city1idx, city2);
		cities.set(city2idx, city1);
	}
	
	public void printRoute() {
		for(City c : cities) {
			System.out.println(c.getId());
		}
	}
	
	public void sortByX() {
		Collections.sort(cities);
	}

}
