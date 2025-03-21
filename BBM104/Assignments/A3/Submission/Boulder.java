/**
 * Represents a boulder game object that extends GameObject.
 * Each Boulder instance is initialized with a random image path.
 */
public class Boulder extends GameObject {

    /**
     * Constructs a Boulder object at the specified position with a random image.
     *
     * @param xPos The x-coordinate position of the Boulder
     * @param yPos The y-coordinate position of the Boulder
     */
    public Boulder(int xPos, int yPos) {
        super(getRandomImagePath(), xPos, yPos);
    }

    /**
     * Generates a random image path for the Boulder.
     *
     * @return A randomly selected image path for the Boulder
     */
    private static String getRandomImagePath() {
        int random = ((int) (Math.random() * 3)) + 1;
        return "assets/underground/obstacle_0" + random + ".png";
    }
}