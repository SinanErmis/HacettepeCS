/**
 * The {@code Route} class represents a route in a map, consisting of a road and
 * the distance from the starting point to the end point of this route.
 */
public class Route {
    public int distance; // The distance of the route from the start point
    public Road road;    // The road associated with this route
    public String point1; // The first endpoint of the route
    public String point2; // The second endpoint of the route

    /**
     * Constructs a {@code Route} object with the specified distance, road, and endpoints.
     *
     * @param distance the distance in KM of the route from the start point
     * @param road the road associated with this route
     * @param point1 the first endpoint of the route
     * @param point2 the second endpoint of the route
     */
    public Route(int distance, Road road, String point1, String point2) {
        this.distance = distance;
        this.road = road;
        this.point1 = point1;
        this.point2 = point2;
    }

    /**
     * Returns the distance of the route from the start point.
     *
     * @return the distance of the route
     */
    public int getDistance() {
        return distance;
    }
}
