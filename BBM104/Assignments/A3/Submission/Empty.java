/**
 * Represents an empty space game object in the underground grid.
 * Extends the GameObject class and initializes with a specific image path.
 */
public class Empty extends GameObject {

    /**
     * Constructs an Empty object at the specified position.
     *
     * @param xPos The x-coordinate position of the Empty object
     * @param yPos The y-coordinate position of the Empty object
     */
    public Empty(double xPos, double yPos) {
        super("assets/underground/empty_15.png", xPos, yPos);
    }
}
