package salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;


public class Route {
	ArrayList<City> cities;
	double totalDistance;
	static boolean DEBUG = true;
	
	public Route(boolean DEBUG) {
		this.cities = new ArrayList<City>();
		Route.DEBUG = DEBUG;
	}
	
	public Route(Route r) {
		this.totalDistance = r.totalDistance;
		this.cities = new ArrayList<City>();
		for(City c : r.cities) {
			this.cities.add(c.clone());
		}
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
			//System.out.println("currlen = " + currLength);
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
		int city1idx = random.nextInt(cities.size());
		int city2idx = city1idx;
		int range = random.nextInt(20);
/*
		while(city2idx == city1idx) {
			city2idx = random.nextInt(cities.size());
		}
*/
		city2idx = city1idx + range;

		if((city1idx + range) > cities.size()) {
			city2idx = cities.size();
		}
		
		if(DEBUG) {
			System.out.printf("Swapping: (idx1 = %d, idx2 = %d)\n", city1idx, city2idx);
		}
		
//		swapCities(city1idx, city2idx);
		reverseRange(city1idx, city2idx);
//		shuffleRange(city1idx,city2idx);
		calculateTotalPathLength();
		
	}
	
	public void swapCities(int city1idx, int city2idx) {
		City city1 = cities.get(city1idx);
		City city2 = cities.get(city2idx);
		
		cities.set(city1idx, city2);
		cities.set(city2idx, city1);
	}
	
	public void reverseRange(int city1idx, int city2idx) {
		ArrayList<City> sublist = new ArrayList<City>(cities.subList(city1idx, city2idx));
		Collections.reverse(sublist);
		int i = city1idx;
		for(City c : sublist) {
			cities.set(i, c);
			i++;
		}
	}
	
	public void shuffleRange(int city1idx, int city2idx) {
		ArrayList<City> sublist = new ArrayList<City>(cities.subList(city1idx, city2idx));
		Collections.shuffle(sublist);
		int i = city1idx;
		for(City c : sublist) {
			cities.set(i, c);
			i++;
		}
	}
	
	public void printRoute() {
//		for(City c : cities) {
//			System.out.println(c);
//		}
//		
		for(int i = 0; i < cities.size()-1; i++) {
			System.out.println(cities.get(i) + " -- " + cities.get(i+1) + ": " + cities.get(i).getDistance(cities.get(i+1)));
		}
		System.out.println("total distance = " + totalDistance);
		
	}
	
	public void printSolution() {
		for(City c : cities) {
			System.out.println(c.getId());
		}
	}
	
	public void sortByX() {
		Collections.sort(cities);
	}

}
