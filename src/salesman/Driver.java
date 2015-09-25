package salesman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Driver {
	
	static boolean DEBUG = true; //0 = not debug, 1 = minimal debug, 2 = more debug
	
	public static void main (String[] args) {
		
		String filename = "data/travelingtest.txt";
		Route route = createRoute(filename, DEBUG);
		double initDist = route.totalDistance;
		if(DEBUG) {
			System.out.println("INITIAL ROUTE DISTANCE:");
			//route.printRoute();
			System.out.println(route.totalDistance);
		}
		Route best = anneal(route);
		
		//best.printRoute();
		System.out.println(best.totalDistance);
		System.out.println("init dist = " + initDist);
		
		
	}
	
	static Route anneal(Route currRoute) {
		Route bestRoute = new Route(currRoute);
		Route newRoute = null;
		
		int temp = 1000000;
		double coolingRate = .00002;
		
		// Loop until system has cooled
		while (temp > 1) {

			// Create new neighbor
			newRoute = new Route(currRoute);

			newRoute.generateNeighbor();
			
			// Get energy of solutions
			double currentEnergy = currRoute.totalDistance;
			double newEnergy = newRoute.totalDistance;

			// Keep track of the best solution found
			if (newEnergy < bestRoute.totalDistance) {
				bestRoute = newRoute;
			}

			// Decide if we should accept the neighbor
			if (acceptanceProbability(currentEnergy, newEnergy, temp) > Math.random()) {
				currRoute = newRoute;
			}

			// Cool system
			temp *= 1 - coolingRate;
		}
		
		return bestRoute;
	}
	
	public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
		if (newEnergy < energy) {
			return 1.0;
		}
		return Math.exp((energy - newEnergy) / temperature);
	}
	
	static Route createRoute(String filename, boolean DEBUG) {
		Route route = new Route(DEBUG);
		
		BufferedReader br = null;
		try {
			
			String line;

			br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				int id = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int z = Integer.parseInt(st.nextToken());
				
				City c = new City(id, x, y, z, DEBUG);
				route.addCity(c);
			}
			route.calculateTotalPathLength();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return route;
	}

}
