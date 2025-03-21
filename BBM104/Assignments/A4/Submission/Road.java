/**
 * The {@code Road} class represents a road in a map with a specified length, ID, 
 * and the two points (endpoints) it connects.
 */
public class Road {
    public String point1, point2; // The endpoints of the road
    public int id;                // The unique identifier of the road
    public int length;            // The length of the road in KMs

    /**
     * Constructs a {@code Road} object with the specified length, ID, and endpoints.
     *
     * @param length the length of the road in KM
     * @param id the unique identifier of the road
     * @param point1 the first endpoint of the road
     * @param point2 the second endpoint of the road
     */
    public Road(int length, int id, String point1, String point2) {
        this.point1 = point1;
        this.point2 = point2;
        this.length = length;
        this.id = id;
    }
}