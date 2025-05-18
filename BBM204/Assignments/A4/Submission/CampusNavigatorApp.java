import java.io.Serializable;
import java.util.*;

public class CampusNavigatorApp implements Serializable {
    static final long serialVersionUID = 99L;

    public HashMap<Station, Station> predecessors = new HashMap<>();
    public HashMap<Set<Station>, Double> times = new HashMap<>();

    public CampusNavigatorNetwork readCampusNavigatorNetwork(String filename) {
        CampusNavigatorNetwork network = new CampusNavigatorNetwork();
        network.readInput(filename);
        return network;
    }

    /**
     * Calculates the fastest route from the user's selected starting point to 
     * the desired destination, using the campus golf cart network and walking paths.
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(CampusNavigatorNetwork network) {

        List<RouteDirection> directions = new ArrayList<>();

        // Step 1: Collect all stations (including start and destination)
        List<Station> allStations = new ArrayList<>();
        Map<Point, Station> pointToStation = new HashMap<>();

        for (CartLine line : network.lines) {
            for (Station station : line.cartLineStations) {
                if (!pointToStation.containsKey(station.coordinates)) {
                    allStations.add(station);
                    pointToStation.put(station.coordinates, station);
                }
            }
        }

        // Ensure start and destination are included
        if (!pointToStation.containsKey(network.startPoint.coordinates)) {
            allStations.add(network.startPoint);
            pointToStation.put(network.startPoint.coordinates, network.startPoint);
        }
        if (!pointToStation.containsKey(network.destinationPoint.coordinates)) {
            allStations.add(network.destinationPoint);
            pointToStation.put(network.destinationPoint.coordinates, network.destinationPoint);
        }

        // Step 2: Build adjacency list and times
        Map<Station, List<Station>> adjacency = new HashMap<>();
        for (Station a : allStations) adjacency.put(a, new ArrayList<>());

        // Walking connections (between all pairs)
        for (Station a : allStations) {
            for (Station b : allStations) {
                if (!a.equals(b)) {
                    double dist = a.coordinates.calculateDistance(b.coordinates);
                    double walkTime = dist / network.averageWalkingSpeed;
                    adjacency.get(a).add(b);
                    times.put(Set.of(a, b), walkTime);
                }
            }
        }

        // Golf cart connections (overwrite walking if applicable)
        for (CartLine line : network.lines) {
            List<Station> s = line.cartLineStations;
            for (int i = 0; i < s.size() - 1; i++) {
                Station a = s.get(i);
                Station b = s.get(i + 1);
                double dist = a.coordinates.calculateDistance(b.coordinates);
                double cartSpeedMPerMin = network.averageCartSpeed;
                double cartTime = dist / cartSpeedMPerMin;

                adjacency.get(a).add(b);
                adjacency.get(b).add(a);
                times.put(Set.of(a, b), cartTime);
            }
        }

        // Step 3: Dijkstra’s algorithm
        Map<Station, Double> dist = new HashMap<>();
        for (Station s : allStations) dist.put(s, Double.POSITIVE_INFINITY);
        dist.put(network.startPoint, 0.0);

        PriorityQueue<Station> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        pq.add(network.startPoint);

        while (!pq.isEmpty()) {
            Station current = pq.poll();
            for (Station neighbor : adjacency.get(current)) {
                double edgeTime = this.times.get(Set.of(current, neighbor));
                double alt = dist.get(current) + edgeTime;
                if (alt < dist.get(neighbor)) {
                    dist.put(neighbor, alt);
                    this.predecessors.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }

        // Step 4: Reconstruct path
        LinkedList<Station> path = new LinkedList<>();
        Station cursor = network.destinationPoint;
        while (cursor != null && this.predecessors.containsKey(cursor)) {
            path.addFirst(cursor);
            cursor = this.predecessors.get(cursor);
        }
        if (cursor == network.startPoint) path.addFirst(cursor);

        // Step 5: Build RouteDirection list
        for (int i = 0; i < path.size() - 1; i++) {
            Station from = path.get(i);
            Station to = path.get(i + 1);
            double duration = this.times.get(Set.of(from, to));
            boolean isCart = false;

            for (CartLine line : network.lines) {
                List<Station> ls = line.cartLineStations;
                for (int j = 0; j < ls.size() - 1; j++) {
                    Station a = ls.get(j);
                    Station b = ls.get(j + 1);
                    if ((a.equals(from) && b.equals(to)) || (a.equals(to) && b.equals(from))) {
                        isCart = true;
                        break;
                    }
                }
                if (isCart) break;
            }

            directions.add(new RouteDirection(from.description, to.description, duration, isCart));
        }

        return directions;
    }

    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        double totalTime = 0;
        for (RouteDirection d : directions) totalTime += d.duration;

        System.out.println("The fastest route takes " + Math.round(totalTime) + " minute(s).");
        System.out.println("Directions\n----------");

        int index = 1;
        for (RouteDirection d : directions) {
            System.out.printf("%d. %s from \"%s\" to \"%s\" for %.2f minutes.%n",
                    index++, d.cartRide ? "Ride the cart" : "Walk", d.startStationName, d.endStationName, d.duration);
        }
    }
}
