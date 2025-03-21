import java.util.*;

/**
 * The {@code FastestRoute} class calculates the shortest path between two points
 * in a graph using a modified version of Dijkstra's algorithm.
 */
public class FastestRoute {
    private final Map<String, Route> fastestRoutes;
    private final String startPoint, endPoint;

    /**
     * Constructs a {@code FastestRoute} to find the shortest path from a start point
     * to an end point using the given roads.
     *
     * @param roads      the list of roads available to connect the points
     * @param startPoint the starting point of the route
     * @param endPoint   the destination point of the route
     */
    public FastestRoute(List<Road> roads, String startPoint, String endPoint) {
        fastestRoutes = new HashMap<>();
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        // Sort roads by length, then by ID
        roads.sort(Comparator.comparingInt((Road r) -> r.length).thenComparingInt(r -> r.id));


        // Initialize the route list with roads starting from the start point
        List<Route> routeList = new ArrayList<>();
        for (Road road : roads) {
            if (road.point1.equals(startPoint) || road.point2.equals(startPoint)) {
                routeList.add(new Route(road.length, road, road.point1, road.point2));
            }
        }

        // Start point has a distance of 0 to itself
        fastestRoutes.put(startPoint, new Route(0, null, startPoint, startPoint));

        // Process the routes until the shortest path to the end point is found
        while (!routeList.isEmpty()) {
            // Sort routeList based on distance and preserve insertion order for equal distances
            routeList.sort(Comparator.comparingInt(Route::getDistance).thenComparingInt(routeList::indexOf));

            // Remove the route with the smallest distance
            Route currentRoute = routeList.remove(0);
            String currentPoint1 = currentRoute.point1;
            String currentPoint2 = currentRoute.point2;

            // Skip if both points are already in the fastestRoutes map
            if (fastestRoutes.containsKey(currentPoint1) && fastestRoutes.containsKey(currentPoint2)) continue;

            // Determine the next point to process
            String nextPoint = fastestRoutes.containsKey(currentPoint1) ? currentPoint2 : currentPoint1;

            // Add the next point to the fastestRoutes map
            fastestRoutes.put(nextPoint, currentRoute);

            // Stop if the end point is reached
            if (nextPoint.equals(endPoint)) break;

            // Add new routes starting from the next point
            for (Road road : roads) {
                if (road.point1.equals(nextPoint) && !fastestRoutes.containsKey(road.point2)) {
                    routeList.add(new Route(currentRoute.distance + road.length, road, road.point1, road.point2));
                } else if (road.point2.equals(nextPoint) && !fastestRoutes.containsKey(road.point1)) {
                    routeList.add(new Route(currentRoute.distance + road.length, road, road.point1, road.point2));
                }
            }
        }
    }

    /**
     * Appends the fastest route from the start point to the end point to the provided StringBuilder.
     *
     * @param sb the StringBuilder to append the route information to
     */
    public void printFastestRoute(StringBuilder sb) {
        List<Route> path = new ArrayList<>();
        String currentPoint = endPoint;

        // Trace back from the end point to the start point to construct the path
        while (!currentPoint.equals(startPoint)) {
            Route route = fastestRoutes.get(currentPoint);
            path.add(route);
            currentPoint = route.point1.equals(currentPoint) ? route.point2 : route.point1;
        }

        Collections.reverse(path);

        // Append the route information to the StringBuilder
        for (Route route : path) {
            sb.append(route.point1).append("\t").append(route.point2).append("\t")
                    .append(route.road.length).append("\t").append(route.road.id).append('\n');
        }
    }

    /**
     * Returns the length of the shortest route from the start point to the end point.
     *
     * @return the length of the fastest route
     */
    public int getLength() {
        return fastestRoutes.get(endPoint).distance;
    }
}