/**
 * Represents a lava game object that extends GameObject.
 * Each Lava instance is initialized with a random image path.
 */
public class Lava extends GameObject {

    /**
     * Constructs a Lava object at the specified position with a random image.
     *
     * @param xPos The x-coordinate position of the Lava
     * @param yPos The y-coordinate position of the Lava
     */
    public Lava(int xPos, int yPos) {
        super(getRandomImagePath(), xPos, yPos);
    }

    /**
     * Generates a random image path for the Lava.
     *
     * @return A randomly selected image path for the Lava
     */
    private static String getRandomImagePath() {
        int random = ((int) (Math.random() * 3)) + 1;
        return "assets/underground/lava_0" + random + ".png";
    }
}