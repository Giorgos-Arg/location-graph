// Reads the input data and creates the graph
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Preprocessor {

	private static final double EARTH_RADIUS = 6371.0; // in km

	public static Graph createGraph(String filePath) {
		Graph graph = new Graph();
		ArrayList<Location> locationList = readInputFile(filePath);
		for (int i = 0; i < locationList.size(); i++) {
			for (int j = i; j < locationList.size(); j++) {
				Location l1 = locationList.get(i);
				Location l2 = locationList.get(j);
				double distance = calculateDistance(l1.getLatitude(), l1.getLongitude(), l2.getLatitude(),
						l2.getLongitude());
				if (l1.getName() != l2.getName()) {
					graph.addEdge(l1.getName(), l2.getName(), distance);
					graph.addEdge(l2.getName(), l1.getName(), distance);
				}
			}
		}
		return graph;
	}

	// Reads the input data and returns a list containing all the locations and their data
	private static ArrayList<Location> readInputFile(String filePath) {
		ArrayList<Location> locationList = new ArrayList<Location>();
		try {
			Scanner scanner = new Scanner(new FileInputStream(filePath));
			scanner.nextLine(); // skip the first line (header)
			while (scanner.hasNextLine()) {
				String[] data = scanner.nextLine().split(",");
				String name = data[2].toLowerCase();
				double latitude = Double.parseDouble(data[0]);
				double longitude = Double.parseDouble(data[1]);
				locationList.add(new Location(name, latitude, longitude));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return locationList;
	}

	// Calculates the distance between two locations in kilometers
	private static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
		double dLatitude = Math.toRadians(latitude2 - latitude1);
		double dLongitude = Math.toRadians(longitude2 - longitude1);
		latitude1 = Math.toRadians(latitude1);
		latitude2 = Math.toRadians(latitude2);
		double a = Math.sin(dLatitude / 2) * Math.sin(dLatitude / 2)
				+ Math.sin(dLongitude / 2) * Math.sin(dLongitude / 2) * Math.cos(latitude1) * Math.cos(latitude2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = EARTH_RADIUS * c;
		return distance;
	}

}

// Class representing a location
class Location {

	private String name;
	private double latitude;
	private double longitude;

	Location(String name, double latitude, double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	String getName() {
		return this.name;
	}

	double getLatitude() {
		return this.latitude;
	}

	double getLongitude() {
		return this.longitude;
	}
}