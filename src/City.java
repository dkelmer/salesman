
public class City implements Comparable<City>{
	int id;
	int x;
	int y;
	int z;
	static boolean DEBUG; 
	
	public City(int id, int x, int y, int z, boolean DEBUG) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		City.DEBUG = DEBUG;
	}
	
	public City() {
		this.id  = this.x = this.y = this.z = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getXcoord() {
		return x;
	}

	public void setXcoord(int xcoord) {
		this.x = xcoord;
	}

	public int getYcoord() {
		return y;
	}

	public void setYcoord(int ycoord) {
		this.y = ycoord;
	}
	
	public int getZcoord() {
		return z;
	}

	public void setZcoord(int zcoord) {
		this.z = zcoord;
	}
	
	public double getDistance(City otherCity) {
		
		double xdiff2 = Math.pow(x - otherCity.getXcoord(), 2.0);
		double ydiff2 = Math.pow(y - otherCity.getYcoord(), 2.0);
		double zdiff2 = Math.pow(z - otherCity.getZcoord(), 2.0);
		double distance = Math.sqrt( xdiff2 + ydiff2 + zdiff2  );
		
//		if(DEBUG) {
//			System.out.println("getting distance between " + this.toString() + " and " + otherCity.toString());
//			System.out.printf("xdiff = %f, ydiff2 = %f, zdiff2 = %f\n", xdiff2, ydiff2, zdiff2);
//			System.out.println("distance = " + distance);
//		}
		return distance;
	}
	
	public City clone() {
		City c = new City();
		c.id = this.id;
		c.x = this.x;
		c.y = this.y;
		c.z = this.z;
		
		return c;
		
	}
	
	public String toString() {
		return String.format("%d: (%d, %d, %d) ", this.id, this.x, this.y, this.z);
	}

	@Override
	public int compareTo(City o) {
		City c = (City) o;
		if(this.x > c.x) {
			return 1;
		}
		else if (this.x < c.x) {
			return -1;
		}
		else {
			return 0;
		}
		
		
	}
	
	
}
