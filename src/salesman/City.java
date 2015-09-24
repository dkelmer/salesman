package salesman;

public class City implements Comparable{
	int id;
	int xcoord;
	int ycoord;
	int zcoord;
	
	public City(int id, int x, int y, int z) {
		this.id = id;
		this.xcoord = x;
		this.ycoord = y;
		this.zcoord = z;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getXcoord() {
		return xcoord;
	}

	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}

	public int getYcoord() {
		return ycoord;
	}

	public void setYcoord(int ycoord) {
		this.ycoord = ycoord;
	}
	
	public int getZcoord() {
		return zcoord;
	}

	public void setZcoord(int zcoord) {
		this.zcoord = zcoord;
	}
	
	public double getDistance(City otherCity) {
		double distance = Math.sqrt( (Math.pow(xcoord - otherCity.getXcoord(), 2.0)) + (Math.pow(ycoord - otherCity.getYcoord(), 2.0)) + (Math.pow(zcoord - otherCity.getZcoord(), 2.0))  );
		
		return distance;
	}
	

	@Override
	public int compareTo(Object o) {
		City c = (City) o;
		if(this.xcoord > c.xcoord) {
			return 1;
		}
		else if (this.xcoord < c.xcoord) {
			return -1;
		}
		else {
			return 0;
		}
		
		
	}
	
	
}
