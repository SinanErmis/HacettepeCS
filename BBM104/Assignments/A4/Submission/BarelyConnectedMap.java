import java.util.*;

//I've had a hard time understanding the instructions in the PDF.
//It turned out, the algorithm that PDF points out works similar to Kruskal's algorithm.
//For more information about MST and Kruskal's Algorithm, see:
//https://www.simplilearn.com/tutorials/data-structure-tutorial/kruskal-algorithm

/**
 * The {@code BarelyConnectedMap} class represents a minimum spanning tree (MST)
 * constructed from a given set of roads and points.
 * It ensures the graph is barely connected using the Kruskal's algorithm.
 */
public class BarelyConnectedMap {
    // Minimum spanning tree (MST) to store generated roads
    private final List<Road> mst = new ArrayList<>();

    /**
     * Constructs a {@code BarelyConnectedMap} from the given roads and points.
     * It uses Kruskal's algorithm to find the MST of the graph.
     *
     * @param roads  the list of roads available to connect the points
     * @param points the set of points to be connected
     */
    public BarelyConnectedMap(List<Road> roads, Set<String> points) {
        Map<String, String> parent = new HashMap<>();
        for (String point : points) {
            parent.put(point, point);
        }

        // Sort roads by length, then by ID
        roads.sort(Comparator.comparingInt((Road r) -> r.length).thenComparingInt(r -> r.id));

        for (Road road : roads) {
            String root1 = find(parent, road.point1);
            String root2 = find(parent, road.point2);

            if (!root1.equals(root2)) {
                mst.add(road);
                parent.put(root1, root2);

                if (mst.size() == points.size() - 1) {
                    break;
                }
            }
        }
    }

    /**
     * Returns the generated minimum spanning tree (MST) as a list of roads.
     *
     * @return the list of roads in the generated MST
     */
    public List<Road> getGeneratedMap() {
        return mst;
    }

    /**
     * Finds the root of the given point in the union-find data structure.
     *
     * @param parent the union-find data structure represented as a map
     * @param point  the point whose root is to be found
     * @return the root of the given point
     */
    private String find(Map<String, String> parent, String point) {
        if (!parent.get(point).equals(point)) {
            parent.put(point, find(parent, parent.get(point)));
        }
        return parent.get(point);
    }
}
