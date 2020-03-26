// Main class
import java.util.Scanner;

public class LocationGraph {

	private static String readLocation(Graph graph, Scanner scanner) {
		String location = scanner.nextLine().toLowerCase();
		if (graph.get(location) == null) {
			System.out.println(location + " is not in the graph.");
			return null;
		}
		return location;
	}

	public static void main(String[] args) {
		if (args.length != 2 || (!args[0].equals("-shortdist") && !args[0].equals("-nclosest"))) {
			System.out.println("Usage:\njava LocationGraph -shortdist <input_file>: print the shortest distance between"
					+ " two locations.\njava LocationGraph -nclosest <input_file>: print the N closest locations from "
					+ "a given location.");
		} else {
			String filePath = args[1];
			System.out.println("\nCreating graph...");
			Graph graph = Preprocessor.createGraph(filePath);
			graph.exportCSV();
			System.out.println("\nGraph is ready! You can view the graph in the file /data/graph.csv.");
			Scanner scanner = new Scanner(System.in);
			if (args[0].equals("-shortdist")) {
				String source, destination;
				do {
					System.out.println("\nGive the first location:");
					source = readLocation(graph, scanner);
				} while (source == null);
				do {
					System.out.println("\nGive the second location:");
					destination = readLocation(graph, scanner);
				} while (destination == null);

				double shortestDistance = graph.getShortestDistance(source, destination);
				if (shortestDistance != -1) {
					System.out.printf("\nThe shortest between %s and %s distance is : %.2f km\n", source, destination,
							shortestDistance);
				}
			} else {
				String location = null;
				do {
					System.out.println("\nGive a location:");
					location = readLocation(graph, scanner);
				} while (location == null);
				int n;
				do {
					System.out.println("\nGive n:");
					n = Integer.parseInt(scanner.nextLine());
					if (n < 1 || n > graph.size()) {
						System.out
								.println("\nn must be between 1 and " + graph.size() + " (number of distinct locations)");
					}
				} while (n < 1 || n > graph.size());

				graph.nClosestLocations(location, n);
			}
			System.out.println();
			scanner.close();
		}
	}
}
