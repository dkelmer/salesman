import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Driver {
	
	static boolean DEBUG = false; //0 = not debug, 1 = minimal debug, 2 = more debug
	
	public static void main (String[] args) {
		
		//String filename = "data/travelingtest.txt";
		String filename = args[0];
		Route route = createRoute(filename, DEBUG);
		ArrayList<City> cities = route.cities;		
		
		//shows bound
		MinimumSpanningTree mst = new MinimumSpanningTree();
		mst.processRoute(route);
		ArrayList<Integer> mstOrder = mst.path();
		ArrayList<City> initialCities = rearrange(cities, mstOrder);
		route.cities = initialCities;
		
		route.calculateTotalPathLength();
		
		double initDist = route.totalDistance;
		if(DEBUG) {
			System.out.println("INITIAL ROUTE DISTANCE:");
			System.out.println(route.totalDistance);
			route.printRoute();
		}
		Route best = anneal(route);
		
		if (DEBUG) {
			//best.printRoute();
			System.out.println("init dist = " + initDist);
			System.out.println(best.totalDistance);
		}
		best.printSolution();
	}
	
	static ArrayList<City> rearrange(ArrayList<City> original, ArrayList<Integer> order) {
		ArrayList<City> result = new ArrayList<City>();
		for (Integer idx : order) {
			result.add(original.get(idx).clone());
		}
		return result;
	}
	
	static Route anneal(Route currRoute) {
		Route bestRoute = new Route(currRoute);
		Route newRoute = null;
		
		//int temp = 100000;
		double temp = .3;
		double coolingRate = .00002;
		
		// Loop until system has cooled
		while (temp > .0001) {

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
			double prob = acceptanceProbability(currentEnergy, newEnergy, temp);
			double rand = Math.random();
			boolean selected = false;
			if (prob > rand) {
				currRoute = newRoute;
				selected = true;
			}
			
			if(DEBUG) {
				System.out.println("current energy: " + currentEnergy);
				System.out.println("new energy: " + newEnergy);
				System.out.println("acceptance prob: " + prob);
				System.out.println("randon num: " + rand);
				System.out.println("switched? " + selected);
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
