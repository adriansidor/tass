package tass;

public class POI {

	private String name;
	
	private double latitude;
	
	private double longtitude;
	
	public POI(String name, double lat, double lng) {
		this.setName(name);
		this.setLatitude(lat);
		this.setLongtitude(lng);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
}
