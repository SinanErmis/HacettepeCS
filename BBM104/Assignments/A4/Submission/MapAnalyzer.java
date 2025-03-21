import java.util.*;

/**
 * The {@code MapAnalyzer} class provides functionality to analyze a map by finding
 * the fastest route between two points and constructing a barely connected map (MST).
 * It reads input from a file, performs the analysis, and writes the results to an output file.
 */
public class MapAnalyzer {
    /**
     * The main method to execute the map analysis.
     *
     * @param args command line arguments where the first argument is the input file name and the second argument is the output file name
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        StringBuilder sb = new StringBuilder();

        // Input and output file names from command line arguments
        String inputFileName = args[0];
        String outputFileName = args[1];

        // Read input file
        String[] lines = FileUtilities.readFile(inputFileName, true, true);
        String[] firstLine = lines[0].split("\t");
        String from = firstLine[0];
        String to = firstLine[1];

        // List to store roads
        List<Road> roads = new ArrayList<>();

        // Parse roads from the input file
        for (int i = 1; i < lines.length; i++) {
            String[] tokens = lines[i].split("\t");
            int length = Integer.parseInt(tokens[2].trim());
            int id = Integer.parseInt(tokens[3].trim());
            roads.add(new Road(length, id, tokens[0], tokens[1]));
        }

        // Find the fastest route in the original map
        FastestRoute fastestRoute = new FastestRoute(roads, from, to);
        sb.append("Fastest Route from ")
                .append(from)
                .append(" to ")
                .append(to)
                .append(" (")
                .append(fastestRoute.getLength())
                .append(" KM):\n");
        fastestRoute.printFastestRoute(sb);

        int fastestRouteLength = fastestRoute.getLength();
        int totalLength = 0;

        // Calculate the total length of all roads
        for (Road road : roads) {
            totalLength += road.length;
        }

        // Set to store unique points
        Set<String> points = new HashSet<>();

        // Add all points from the roads to the set
        for (Road road : roads) {
            points.add(road.point1);
            points.add(road.point2);
        }

        // Generate the Barely Connected Map (Minimum Spanning Tree)
        List<Road> bcm = new BarelyConnectedMap(roads, points).getGeneratedMap();
        sb.append("Roads of Barely Connected Map is:\n");

        // Append the roads in the Barely Connected Map to the StringBuilder
        for (Road road : bcm) {
            sb.append(road.point1)
                    .append('\t')
                    .append(road.point2)
                    .append('\t')
                    .append(road.length)
                    .append('\t')
                    .append(road.id)
                    .append('\n');
        }

        // Find the fastest route in the Barely Connected Map
        fastestRoute = new FastestRoute(bcm, from, to);
        sb.append("Fastest Route from ")
                .append(from)
                .append(" to ")
                .append(to)
                .append(" on Barely Connected Map (")
                .append(fastestRoute.getLength())
                .append(" KM):\n");
        fastestRoute.printFastestRoute(sb);

        int bcmLength = fastestRoute.getLength();
        int bcmTotalLength = 0;

        // Calculate the total length of roads in the Barely Connected Map
        for (Road road : bcm) {
            bcmTotalLength += road.length;
        }

        // Append the analysis results to the StringBuilder
        sb.append("Analysis:\nRatio of Construction Material Usage Between Barely Connected and Original Map: ")
                .append(String.format("%.2f", (double) bcmTotalLength / totalLength))
                .append("\nRatio of Fastest Route Between Barely Connected and Original Map: ")
                .append(String.format("%.2f", (double) bcmLength / fastestRouteLength));

        // Write the results to the output file
        FileUtilities.writeToFile(outputFileName, sb.toString(), false, false);
    }
}