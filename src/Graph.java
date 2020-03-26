
// This class implements a graph using adjacency lists with a maximum size of 5.
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;

public class Graph {
	private static int MAX_LIST_SIZE = 10;
	private Map<String, LinkedList<Node>> map;

	public Graph() {
		map = new HashMap<>();
	}

	public void addVertex(String s) {
		map.put(s, new LinkedList<Node>());
	}

	// Adds an edge between source and destination
	public void addEdge(String source, String destination, double distance) {
		if (!map.containsKey(source)) {
			addVertex(source);
		}
		Node node = new Node(destination, distance);
		LinkedList<Node> list1 = map.get(source);
		list1.add(node);
		list1.sort(new SortByDistance());
		if (list1.size() > MAX_LIST_SIZE) {
			list1.remove(MAX_LIST_SIZE);
		}
	}

	public int size() {
		return map.size();
	}

	// returns the adjacency list of the location s
	public LinkedList<Node> get(String s) {
		return map.get(s);
	}

	// Prints the graph in a .csv file
	public void exportCSV() {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("../data/graph.csv");
			for (Map.Entry<String, LinkedList<Node>> mapElement : map.entrySet()) {
				String locationName = (String) mapElement.getKey();
				LinkedList<Node> list = (LinkedList<Node>) mapElement.getValue();
				for (Node node : list) {
					fileWriter.append(locationName + "," + node.getName() + "," + node.getDistance() + "\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Finds the shortest distance between two locations using Dijkstra's algorithm.
	public double getShortestDistance(String source, String destination) {
		HashSet<String> visited = new HashSet<String>();
		HashMap<String, Double> distance = new HashMap<String, Double>();
		visited.add(source);
		distance.put(source, 0.0);
		for (int i = 0; i < get(source).size(); i++) {
			distance.put(get(source).get(i).getName(), get(source).get(i).getDistance());
		}
		String s = source;
		while (!s.equals(destination)) {
			s = minVertex(visited, distance);
			if (s == null) {
				System.out.println("\nCould not find a path from " + source + " to " + destination
						+ " as the graph is not fully connected (each location is only connected with it's "
						+ MAX_LIST_SIZE + " closest locations).");
				break;
			}
			visited.add(s);
			for (int i = 0; i < get(s).size(); i++) {
				Node node = get(s).get(i);
				if (!distance.containsKey(node.getName())) {
					distance.put(node.getName(), Double.POSITIVE_INFINITY);
				}
				if (distance.get(node.getName()) > distance.get(s) + node.getDistance()) {
					distance.remove(node.getName());
					distance.put(node.getName(), distance.get(s) + node.getDistance());
				}
			}
		}
		if (s == null) {
			return -1;
		}
		return distance.get(s);
	}

	// Finds the n closest locations from a location using Dijkstra's algorithm.
	public void nClosestLocations(String location, int n) {
		HashSet<String> visited = new HashSet<String>();
		HashMap<String, Double> distance = new HashMap<String, Double>();
		visited.add(location);
		distance.put(location, 0.0);
		System.out.println("\nThe " + n + " closest locations from " + location + " are:");
		for (int i = 0; i < get(location).size(); i++) {
			distance.put(get(location).get(i).getName(), get(location).get(i).getDistance());
		}
		for (int i = 0; i < n; i++) {
			String s = minVertex(visited, distance);
			if (s == null) {
				System.out.println("\nNo more locations can be found as the graph is not fully connected (each location "
						+ "is only connected with it's " + MAX_LIST_SIZE + " closest locations).");
				break;
			}
			visited.add(s);
			System.out.println((i + 1) + ": " + s);
			for (int j = 0; j < get(s).size(); j++) {
				Node node = get(s).get(j);
				if (!distance.containsKey(node.getName())) {
					distance.put(node.getName(), Double.POSITIVE_INFINITY);
				}
				if (distance.get(node.getName()) > distance.get(s) + node.getDistance()) {
					distance.remove(node.getName());
					distance.put(node.getName(), distance.get(s) + node.getDistance());
				}
			}
		}
	}

	// Finds the unvisited vertex with the smallest weight.
	public String minVertex(HashSet<String> visited, HashMap<String, Double> distance) {
		String minV = null;
		double min = Double.POSITIVE_INFINITY;
		for (Map.Entry<String, Double> mapElement : distance.entrySet()) {
			String locationName = (String) mapElement.getKey();
			if (visited.contains(locationName)) {
				continue;
			}
			if (distance.get(locationName) < min) {
				min = distance.get(locationName);
				minV = locationName;
			}
		}
		return minV;
	}
}

// Class representing a node in the graph's adjacency lists
class Node {

	private String name;
	private double distance;

	public Node(String name, double distance) {
		this.name = name;
		this.distance = distance;
	}

	public String getName() {
		return this.name;
	}

	public double getDistance() {
		return this.distance;
	}
}

class SortByDistance implements Comparator<Node> {
	// Used for sorting in ascending order of distance
	public int compare(Node a, Node b) {
		return Double.compare(a.getDistance(), b.getDistance());
	}
}
