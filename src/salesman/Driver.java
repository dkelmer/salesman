package salesman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Driver {
	public static void main (String[] args) {
		
		Route route = new Route();
		
		BufferedReader br = null;
		String filename = "data/travelingtest.txt";
		try {

			String line;

			br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				int id = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int z = Integer.parseInt(st.nextToken());
				
				City c = new City(id, x, y, z);
				route.addCity(c);
			}
			
			route.calculateTotalPathLength();
			System.out.println(route.totalDistance);
			route.sortByX();
			route.calculateTotalPathLength();
			System.out.println(route.totalDistance);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
